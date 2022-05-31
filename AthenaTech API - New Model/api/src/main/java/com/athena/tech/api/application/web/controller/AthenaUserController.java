package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.user.AthenaUserAuthenticate;
import com.athena.tech.api.application.web.request.user.AthenaUserRegisterRequest;
import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserAuthenticateResponse;
import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserFormatedResponse;
import com.athena.tech.api.domain.entity.user.AthenaUserObj;
import com.athena.tech.api.domain.entity.user.AuthenticateUser;
import com.athena.tech.api.domain.service.AthenaUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class AthenaUserController{

    @Autowired
    private AthenaUserService athenaUserService = new AthenaUserService();

    //TODO OK
    @PostMapping
    public ResponseEntity<Void> postNewUser(
            @RequestBody @Valid AthenaUserRegisterRequest athenaUser
    ) {
        return athenaUserService.createNewUser(athenaUser);
    }

    //TODO OK
    @PostMapping("/auth")
    public ResponseEntity<AuthenticateUser> LoginUser(
            @RequestBody @Valid AthenaUserAuthenticate athenaUserAuthenticate
    ) {
        return athenaUserService.loginUser(athenaUserAuthenticate);
    }

    //TODO OK
    @DeleteMapping("/auth/{idUser}")
    public ResponseEntity<AthenaUserAuthenticateResponse> logOutUser(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.logoffUser(idUser);
    }

    //TODO OK
    @GetMapping("/auth/get-all")
    public ResponseEntity<AuthenticateUser> getAllAuthenticated() {
        return athenaUserService.getAllAuthenticated();
    }

    //TODO Revisar query de busca de id
    @GetMapping("/{idUser}/get-all")
    public ResponseEntity<List<AthenaUserFormatedResponse>> getAllUsersFormated(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.getAllUsersService(idUser);
    }

    //TODO OK
    @GetMapping("/{idUser}/info")
    public ResponseEntity<AthenaUserFormatedResponse> getUserByIdFormated(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.getUserByIdFormated(idUser);
    }

    //TODO  OK
    @GetMapping("/{idUser}/full-info")
    public ResponseEntity<AthenaUserObj> getUserByIdFullInfo(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.getUserByIdCompleteInfo(idUser);
    }

    //TODO OK
    @PutMapping("/{idUser}/change-password/{newPassword}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Integer idUser,
            @PathVariable String newPassword
    ) {
        return athenaUserService.updatePassword(idUser, newPassword);
    }

    //TODO OK
    @PutMapping("/{idUser}/change-fullname/{newFullName}")
    public ResponseEntity<Void> updateFullName(
            @PathVariable Integer idUser,
            @PathVariable String newFullName
    ) {
        return athenaUserService.updateFullname(idUser, newFullName);
    }

    //TODO OK
    @PutMapping("/{idUser}/change-nickname/{newNickname}")
    public ResponseEntity<Void> updateNickname(
            @PathVariable Integer idUser,
            @PathVariable String newNickname
    ) {
        return athenaUserService.updateNickname(idUser, newNickname);
    }

    //TODO OK
    @PutMapping("/{idUser}/change-email/{newEmail}")
    public ResponseEntity<Void> updateEmail(
            @PathVariable Integer idUser,
            @PathVariable String newEmail
    ) {
        return athenaUserService.updateEmail(idUser, newEmail);
    }

    //TODO OK
    @PutMapping("/{idUser}/reactive")
    public ResponseEntity<Void> reactiveAccount(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.reactiveUser(idUser);
    }


    //TODO OK
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer idUser
    ) {
        return athenaUserService.deleteAthenaUser(idUser);
    }
}
