package com.athena.tech.api.application.web.controller;

import com.athena.tech.api.application.web.request.user.AthenaUserAuthenticate;
import com.athena.tech.api.application.web.request.user.AthenaUserRegisterRequest;
import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserFormatedResponse;
import com.athena.tech.api.domain.commons.adapters.AthenaUserAdapter;
import com.athena.tech.api.domain.entity.user.AuthenticateUser;
import com.athena.tech.api.domain.repositories.AthenaUserRepository;
import com.athena.tech.api.domain.service.AthenaUserService;
import com.athena.tech.api.resources.orm.AthenaUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.athena.tech.api.domain.enums.AccountType.STUDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AthenaUserController.class})
class AthenaUserControllerTest {

    @MockBean
    AthenaUserService athenaUserService;

    @Autowired
    AthenaUserController controller;

    ResponseEntity created = new ResponseEntity(HttpStatus.CREATED);
    ResponseEntity conflict = new ResponseEntity(HttpStatus.CONFLICT);
    ResponseEntity badRequest = new ResponseEntity(HttpStatus.BAD_REQUEST);
    ResponseEntity ok = new ResponseEntity(HttpStatus.OK);
    ResponseEntity notFound = new ResponseEntity(HttpStatus.NOT_FOUND);
    ResponseEntity unauthorized = new ResponseEntity(HttpStatus.UNAUTHORIZED);
    ResponseEntity notAcceptable = new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

    @Test
    @DisplayName("Cadastro CORRETO")
    void postCorrectUser() {
        AthenaUserRegisterRequest userMocked = mock(AthenaUserRegisterRequest.class);

        when(athenaUserService.createNewUser(userMocked)).thenReturn(created);

        ResponseEntity<Void> response = controller.postNewUser(userMocked);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("createNewUser() INCORRETO - USUÁRIO JÁ CADASTRADO")
    void postUserAlreadyRegistered() {
        AthenaUserRegisterRequest userMocked = new AthenaUserRegisterRequest
                (
                        "Teste",
                        "teste@teste.com.br",
                        "teste",
                        "teste",
                        LocalDate.now(),
                        STUDENT
                );

        AthenaUserRegisterRequest userMocked1 = new AthenaUserRegisterRequest
                (
                        userMocked.getFullName(),
                        userMocked.getEmail(),
                        userMocked.getNickname(),
                        userMocked.getPassword(),
                        userMocked.getBirthDate(),
                        userMocked.getAccountType()
                );

        when(athenaUserService.createNewUser(userMocked)).thenReturn(created);
        when(athenaUserService.createNewUser(userMocked1)).thenReturn(conflict);

        ResponseEntity<Void> responseCreated = controller.postNewUser(userMocked);
        ResponseEntity<Void> responseConflict = controller.postNewUser(userMocked1);

        assertEquals(201, responseCreated.getStatusCodeValue());
        assertEquals(409, responseConflict.getStatusCodeValue());
    }

    @Test
    @DisplayName("createNewUser() INVÁLIDO")
    void postInvalidUser() {
        AthenaUserRegisterRequest userMocked = new AthenaUserRegisterRequest
                (
                        "",
                        "teste",
                        "t",
                        "te",
                        LocalDate.now(), STUDENT
                );

        when(athenaUserService.createNewUser(userMocked)).thenReturn(badRequest);

        ResponseEntity<Void> response = controller.postNewUser(userMocked);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("getUserByIdFormated() CORRETO")
    void getCorrectUserByIdFormated() {
        AthenaUser userMocked = new AthenaUser();

        when(athenaUserService.getUserByIdFormated(userMocked.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.getUserByIdFormated(userMocked.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteAthenaUser() CORRETO")
    void deleteUserOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.deleteAthenaUser(userMocked.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.deleteUser(userMocked.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteAthenaUser() INCORRETO")
    void deleteUserNotOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.deleteAthenaUser(1)).thenReturn(notFound);

        ResponseEntity response = controller.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateFullName() CORRETO")
    void updateFullNameOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.updateFullname(userMocked.getIdUser(), "teste")).thenReturn(ok);

        ResponseEntity response = controller.updateFullName(userMocked.getIdUser(), "teste");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateFullName() NÃO ENCONTRADO")
    void updateFullNameNotFound() {
        when(athenaUserService.updateFullname(1, "teste")).thenReturn(notFound);

        ResponseEntity response = controller.updateFullName(1, "teste");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateFullName() NÃO AUTORIZADO")
    void updateFullNameUnauthorized() {
        AthenaUser userMocked = mock(AthenaUser.class);
        userMocked.setIsActive(0);

        when(athenaUserService.updateFullname(userMocked.getIdUser(), "teste")).thenReturn(unauthorized);

        ResponseEntity response = controller.updateFullName(userMocked.getIdUser(), "teste");

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateNickname() CORRETO")
    void updateNicknameOk() {
        AthenaUser userMocked = mock(AthenaUser.class);
        AthenaUser userMocked1 = mock(AthenaUser.class);
        userMocked.setNickname("Teste");

        when(athenaUserService.updateNickname(userMocked1.getIdUser(), "testee")).thenReturn(ok);

        ResponseEntity response = controller.updateNickname(userMocked1.getIdUser(), "testee");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateNickname() INCORRETO")
    void updateNicknameNotOk() {
        AthenaUser userMocked = mock(AthenaUser.class);
        AthenaUser userMocked1 = mock(AthenaUser.class);
        userMocked.setNickname("Teste");

        when(athenaUserService.updateNickname(userMocked1.getIdUser(), "Teste")).thenReturn(notAcceptable);

        ResponseEntity response = controller.updateNickname(userMocked1.getIdUser(), "Teste");

        assertEquals(406, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateNickname() NÃO AUTORIZADO")
    void updateNicknameUnauthorized() {
        AthenaUser userMocked1 = mock(AthenaUser.class);
        userMocked1.setIsActive(0);

        when(athenaUserService.updateNickname(userMocked1.getIdUser(), "Teste")).thenReturn(unauthorized);

        ResponseEntity response = controller.updateNickname(userMocked1.getIdUser(), "Teste");

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateNickname() NÃO ENCONTRADO")
    void updateNicknameNotFound() {
        when(athenaUserService.updateNickname(1, "Teste")).thenReturn(notFound);

        ResponseEntity response = controller.updateNickname(1, "Teste");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updatePassword() CORRETO")
    void updatePasswordOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.updatePassword(userMocked.getIdUser(), "teste")).thenReturn(ok);

        ResponseEntity response = controller.updatePassword(userMocked.getIdUser(), "teste");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updatePassword() NÃO AUTORIZADO")
    void updatePasswordUnauthorized() {
        AthenaUser userMocked = mock(AthenaUser.class);
        userMocked.setIsActive(0);

        when(athenaUserService.updatePassword(userMocked.getIdUser(), "teste")).thenReturn(unauthorized);

        ResponseEntity response = controller.updatePassword(userMocked.getIdUser(), "teste");

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updatePassword() NÃO ENCONTRADO")
    void updatePasswordNotFound() {
        when(athenaUserService.updatePassword(1, "teste")).thenReturn(notFound);

        ResponseEntity response = controller.updatePassword(1, "teste");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateEmail() CORRETO")
    void updateEmailOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.updateEmail(userMocked.getIdUser(), "teste@teste.com.br")).thenReturn(ok);

        ResponseEntity response = controller.updateEmail(userMocked.getIdUser(), "teste@teste.com.br");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateEmail() NÃO ENCONTRADO")
    void updateEmailNotFound() {
        when(athenaUserService.updateEmail(1, "teste@teste.com.br")).thenReturn(notFound);

        ResponseEntity response = controller.updateEmail(1, "teste@teste.com.br");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("updateEmail() NÃO AUTORIZADO")
    void updateEmailUnauthorized() {
        AthenaUser userMocked = mock(AthenaUser.class);
        userMocked.setIsActive(0);

        when(athenaUserService.updateEmail(userMocked.getIdUser(), "teste@teste.com.br")).thenReturn(unauthorized);

        ResponseEntity response = controller.updateEmail(userMocked.getIdUser(), "teste@teste.com.br");

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("reactiveUser() CORRETO")
    void reactiveUserOk() {
        AthenaUser userMocked = mock(AthenaUser.class);
        userMocked.setIsActive(0);

        when(athenaUserService.reactiveUser(userMocked.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.reactiveAccount(userMocked.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("reactiveUser() NÃO ENCONTRADO")
    void reactiveUserNotFound() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.reactiveUser(1)).thenReturn(notFound);

        ResponseEntity response = controller.reactiveAccount(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("reactiveUser() NÃO AUTORIZADO")
    void reactiveUserUnauthorized() {
        AthenaUser userMocked = mock(AthenaUser.class);
        userMocked.setIsActive(0);

        when(athenaUserService.reactiveUser(userMocked.getIdUser())).thenReturn(unauthorized);

        ResponseEntity response = controller.reactiveAccount(userMocked.getIdUser());

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("loginUser() CORRETO")
    void loginUserOk() {
        AthenaUserAuthenticate userMocked = mock(AthenaUserAuthenticate.class);

        when(athenaUserService.loginUser(userMocked)).thenReturn(ok);

        ResponseEntity response = controller.LoginUser(userMocked);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("loginUser() NÃO ENCONTRADO")
    void loginUserNotFound() {
        AthenaUserAuthenticate userMocked = mock(AthenaUserAuthenticate.class);

        when(athenaUserService.loginUser(null)).thenReturn(notFound);

        ResponseEntity response = controller.LoginUser(null);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("logOutUser() CORRETO")
    void logOutUserOk() {
        AthenaUser userMocked = mock(AthenaUser.class);

        when(athenaUserService.logoffUser(userMocked.getIdUser())).thenReturn(ok);

        ResponseEntity response = controller.logOutUser(userMocked.getIdUser());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("logOutUser() NÃO ENCONTRADO")
    void logOutUserNotFound() {
        when(athenaUserService.logoffUser(1)).thenReturn(notFound);

        ResponseEntity response = controller.logOutUser(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
