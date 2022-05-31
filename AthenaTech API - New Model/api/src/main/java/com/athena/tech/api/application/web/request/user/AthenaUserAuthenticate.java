package com.athena.tech.api.application.web.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AthenaUserAuthenticate {

    @NotBlank
    @Size(max=80)
    private String nickname;
    @NotBlank
    @Size(max=40)
    private String password;

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
}
