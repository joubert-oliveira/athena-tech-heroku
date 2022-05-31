package com.athena.tech.api.domain.entity.user;

import com.athena.tech.api.domain.enums.AccountType;

public class AuthenticateUser {

    Integer id;
    AccountType accountType;
    Boolean isActive;

    public AuthenticateUser(Integer id, AccountType accountType, Boolean isActive) {
        this.id = id;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
