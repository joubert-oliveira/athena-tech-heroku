package com.athena.tech.api.application.web.request.user;

import com.athena.tech.api.domain.enums.AccountType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AthenaUserRegisterRequest {

    @NotBlank
    @Size(min = 10, max = 80)
    private String fullName;

    @NotBlank
    @Size(max = 80)
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 80)
    private String nickname;

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    @DateTimeFormat
    private LocalDate birthDate;

    private AccountType accountType;

    public AthenaUserRegisterRequest(String fullName, String email, String nickname, String password, LocalDate birthDate, AccountType accountType) {
        this.fullName = fullName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.birthDate = birthDate;
        this.accountType = accountType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
