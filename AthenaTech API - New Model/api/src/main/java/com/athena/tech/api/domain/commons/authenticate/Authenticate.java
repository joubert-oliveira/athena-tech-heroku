package com.athena.tech.api.domain.commons.authenticate;

import com.athena.tech.api.application.web.response.ErrorMessageResponse;
import com.athena.tech.api.application.web.response.GenericMessageResponse;
import com.athena.tech.api.domain.entity.user.AuthenticateUser;
import com.athena.tech.api.domain.enums.AccountType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class Authenticate {
    static List<AuthenticateUser> authenticateUsers = new ArrayList();

    public ResponseEntity loginUser(AuthenticateUser value) {
        try {
            Boolean exist = false;
            for (int i = 0; i < authenticateUsers.size(); i++) {
                if (authenticateUsers.get(i).getId().equals(value.getId())) {
                    exist = true;
                }
            }
            if (!exist) {
                authenticateUsers.add(value);
                return ResponseEntity.status(201).body(value);
            }
            return ResponseEntity.status(200).body(new GenericMessageResponse("Authenticated", "Ja authenticado"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessageResponse("Conversion Error", "Exception: " + e));
        }
    }

    public ResponseEntity logoutUser(Integer id) {
        int index = -1;
        for (int i = 0; i < authenticateUsers.size(); i++) {
            if (authenticateUsers.get(i).getId().equals(id)) {
                index = i;
            }
        }
        if (index == -1) {
            return ResponseEntity.status(404).body(new ErrorMessageResponse("Not Found", "Usuario não authenticado"));
        } else {
            authenticateUsers.remove(index);
            return ResponseEntity.status(200).body(new GenericMessageResponse("LogOut Successfully", "LogOut Efetuado"));
        }

    }

    public List<AuthenticateUser> getAll() {
        return authenticateUsers;
    }

    public Boolean authenticateGeneric(Integer id) {
        for (AuthenticateUser authenticateUser : authenticateUsers) {
            if (authenticateUser.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Boolean authenticateInstructor(Integer id) {
        for (AuthenticateUser authenticateUser : authenticateUsers) {
            if (authenticateUser.getId().equals(id)
                    && authenticateUser.getId().equals(id)
                    && authenticateUser.getAccountType().equals(AccountType.INSTRUCTOR)) {
                return true;
            }
        }
        return false;
    }
}