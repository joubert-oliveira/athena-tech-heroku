package com.athena.tech.api.application.web.response.AthenaUser;

public class InativeAccount {
    private String messageType;
    private String message;
    private Integer id;

    public InativeAccount(String messageType, String message, Integer id) {
        this.messageType = messageType;
        this.message = message;
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
