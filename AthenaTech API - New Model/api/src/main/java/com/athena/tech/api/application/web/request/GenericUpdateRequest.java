package com.athena.tech.api.application.web.request;

import com.athena.tech.api.domain.entity.user.AuthenticateUser;

import javax.validation.constraints.NotBlank;

public class GenericUpdateRequest {
    private Integer id;
    @NotBlank
    private String content;

    public GenericUpdateRequest(Integer id, String content, AuthenticateUser authenticateUser) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
