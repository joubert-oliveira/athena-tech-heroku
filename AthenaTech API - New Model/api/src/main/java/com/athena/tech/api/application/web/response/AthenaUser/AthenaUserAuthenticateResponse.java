package com.athena.tech.api.application.web.response.AthenaUser;

import com.athena.tech.api.domain.enums.AccountType;

public class AthenaUserAuthenticateResponse {
    private Integer idUser;
    private String fullName;
    private String nickname;
    private AccountType accountType;

    public AthenaUserAuthenticateResponse(Integer idUser, String fullName, String nickname, AccountType accountType) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.nickname = nickname;
        this.accountType = accountType;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
