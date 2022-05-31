package com.athena.tech.api.domain.entity.user;

import com.athena.tech.api.domain.commons.adapters.AthenaUserAdapter;
import com.athena.tech.api.domain.commons.adapters.GenericAdapter;

public class AthenaUserReport {
    private Integer idUser;
    private String fullName;
    private String nickname;
    private String accountType;
    private String isActive;

    public AthenaUserReport(Integer idUser, String fullName, String nickname, String accountType, String isActive) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.nickname = nickname;
        this.accountType = accountType;
        this.isActive = isActive;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
