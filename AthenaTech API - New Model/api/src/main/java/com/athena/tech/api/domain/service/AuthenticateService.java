package com.athena.tech.api.domain.service;

import com.athena.tech.api.application.web.request.user.AthenaUserAuthenticate;
import com.athena.tech.api.application.web.response.AthenaUser.InativeAccount;
import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.commons.adapters.AthenaUserAdapter;
import com.athena.tech.api.domain.commons.adapters.GenericAdapter;
import com.athena.tech.api.domain.commons.authenticate.Authenticate;
import com.athena.tech.api.domain.entity.user.AuthenticateUser;
import com.athena.tech.api.domain.repositories.AthenaUserRepository;
import com.athena.tech.api.resources.orm.AthenaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class AuthenticateService {

    @Autowired
    public AthenaUserRepository athenaUserRepository;
    public AthenaUserAdapter athenaUserAdapter = new AthenaUserAdapter();
    public GenericAdapter genericAdapter = new GenericAdapter();

    public Authenticate authenticate = new Authenticate();

    public ResponseEntity loginUser(AthenaUserAuthenticate athenaUserAuthenticate) {
        try {
            AthenaUser athenaUser = athenaUserRepository.findByNicknameAndPassword(
                    athenaUserAuthenticate.getNickname(),
                    athenaUserAuthenticate.getPassword()
            );
            if (athenaUser == null) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Nem um usuario foi encontrado"));
            }
            if (athenaUser.getIsActive().equals(0)){
                return ResponseEntity.status(401).body(new InativeAccount("Not authorized", "O usuario não está ativo", athenaUser.getIdUser()));
            }
            return authenticate.loginUser(
                    new AuthenticateUser(
                            athenaUser.getIdUser(),
                            athenaUserAdapter.adapterStringToAccountType(athenaUser.getAccountType()
                            ),
                            genericAdapter.adapterBitToBolean(athenaUser.getIsActive())
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity logoffUser(Integer id) {
        try {
            Boolean exists = athenaUserRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.status(404).body(new GenericMessageResponse("Not Found", "Nem um usuario foi encontrado"));
            }
            return authenticate.logoutUser(id);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericMessageResponse("Bad Request", "Exception: " + e));
        }
    }

    public ResponseEntity getAllAuthenticated() {
        return ResponseEntity.status(200).body(authenticate.getAll());
    }
}
