package com.athena.tech.api.application.web.response.AthenaUser;

import com.athena.tech.api.domain.enums.AccountType;

public class AthenaUserFormatedResponse {
    private String fullName;
    private String nickname;
    private String email;
    private AccountType accountType;
    private Boolean isActive;

    public AthenaUserFormatedResponse(String fullName, String nickname, String email, AccountType accountType, Boolean isActive) {
        this.fullName = fullName;
        this.nickname = nickname;
        this.email = email;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
