package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.request.user.AthenaUserRegisterRequest;
import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserFormatedResponse;
import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.commons.adapters.AthenaUserAdapter;
import com.athena.tech.api.domain.commons.authenticate.Authenticate;
import com.athena.tech.api.domain.entity.user.AthenaUserObj;
import com.athena.tech.api.domain.repositories.AthenaUserRepository;
import com.athena.tech.api.resources.orm.AthenaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AthenaUserService extends AuthenticateService {

    @Autowired
    AthenaUserRepository athenaUserRepository;
    AthenaUserAdapter athenaUserAdapter = new AthenaUserAdapter();
    Authenticate authenticate = super.authenticate;

    public ResponseEntity getAllUsersService(Integer id) {
        try{
            if (!authenticate.authenticateGeneric(id)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            List<AthenaUserFormatedResponse> response = new ArrayList<>();
            athenaUserRepository
                    .getAllAthenaUsers()
                    .stream()
                    .forEach(
                            athenaUserObj ->
                                    response.add(athenaUserAdapter.adapterAthenaUserObjToAthenaUserFormatedResponse(athenaUserObj))
                    );

            if (response.isEmpty()){
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity getUserByIdFormated(Integer id) {
        try {
            if (!authenticate.authenticateGeneric(id)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }

            AthenaUserObj getByIdConsult = athenaUserRepository.findUserById(id);
            if (getByIdConsult == null){
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(200).body(athenaUserAdapter.adapterAthenaUserObjToAthenaUserFormatedResponse(getByIdConsult));
            }
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity getUserByIdCompleteInfo(Integer id) {
        try {
            if (!authenticate.authenticateGeneric(id)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }

            AthenaUserObj response = (athenaUserRepository.findUserById(id));
            if (response == null){
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "O Usuario com o Id " + id + "Não foi encontrado"));
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity createNewUser(AthenaUserRegisterRequest athenaUserRegisterRequest){
        try {
            AthenaUser athenaUserObj = athenaUserRepository.findByNickname(athenaUserRegisterRequest.getNickname());
            if (athenaUserObj != null){
                return ResponseEntity.status(409).body(new GenericMessageResponse("Create Conflict", "Login já está em uso"));
            }
            athenaUserRepository.createNewAthenaUser(
                    athenaUserRegisterRequest.getFullName(),
                    athenaUserRegisterRequest.getNickname(),
                    athenaUserRegisterRequest.getEmail(),
                    athenaUserRegisterRequest.getPassword(),
                    athenaUserRegisterRequest.getAccountType().toString(),
                    1,
                    athenaUserRegisterRequest.getBirthDate()
            );
            return ResponseEntity.status(201).body(new GenericMessageResponse("Successfully Created", "Usuario criado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity updatePassword(Integer idUser, String content) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            athenaUserRepository.updatePassword(idUser, content);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Updated", "Password atualizado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity updateEmail(Integer idUser, String content) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            athenaUserRepository.updateEmail(idUser, content);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Updated", "Password atualizado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity updateNickname(Integer idUser, String content) {
        try {
            AthenaUser athenaUser = athenaUserRepository.findAthenaUserByIdUser(idUser);
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            if (athenaUser.getNickname().equals(content)){
                return ResponseEntity.status(406).body(new GenericMessageResponse("Conflict", "O nome de usuario ja está em uso"));
            }
            AthenaUser verify = athenaUserRepository.findByNickname(content);

            if (verify != null){
                return ResponseEntity.status(409).build();
            }
            
            athenaUserRepository.updateNickname(idUser, content);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Updated", "Password atualizado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity updateFullname(Integer idUser, String content) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            athenaUserRepository.updateFullName(idUser, content);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Updated", "Password atualizado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity deleteAthenaUser(Integer idUser) {
        try {
            athenaUserRepository.updateUserToInative(idUser);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successfully Delete", "Usuario deletado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

    public ResponseEntity reactiveUser(Integer idUser) {
        try {
            if (!authenticate.authenticateGeneric(idUser)) {
                return ResponseEntity.status(401).body(new ErrorMessageResponse("Not Authorized", "O usuario precisa estar authenticado"));
            }
            athenaUserRepository.reactiveAthenaUser(idUser);
            return ResponseEntity.status(200).body(new GenericMessageResponse("Successful Reactivation", "Usuario reativado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.status(404).body(new ErrorMessageResponse("NotFound", "Exception: " + e));
        }
    }

}
