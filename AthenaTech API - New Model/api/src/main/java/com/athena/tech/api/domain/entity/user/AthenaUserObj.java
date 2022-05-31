package com.athena.tech.api.domain.entity.user;

import com.athena.tech.api.domain.commons.adapters.AthenaUserAdapter;
import com.athena.tech.api.domain.commons.adapters.GenericAdapter;
import com.athena.tech.api.domain.enums.AccountType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class AthenaUserObj {
    private Integer idUser;
    @NotBlank
    @Length(max = 80, min = 3)
    private String fullName;
    @NotBlank
    @Length(max = 80, min = 3)
    private String nickname;
    @Email
    private String email;
    @NotBlank
    @Length(max = 30, min = 8)
    private String password;
    @NotBlank
    private AccountType accountType;
    @NotBlank
    private Boolean isActive = true;

    public AthenaUserObj(Integer idUser,String fullName, String nickname, String email, String password, String accountType, Integer isActive) {
        AthenaUserAdapter athenaUserAdapter = new AthenaUserAdapter();
        GenericAdapter genericAdapter = new GenericAdapter();

        this.idUser = idUser;
        this.fullName = fullName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.accountType = athenaUserAdapter.adapterStringToAccountType(accountType);
        this.isActive = genericAdapter.adapterBitToBolean(isActive);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
