package com.athena.tech.api.application.web.response;

public class GenericMessageResponse {
    private String messageType;
    private String message;

    public GenericMessageResponse(String messageType, String message) {
        this.messageType = messageType;
        this.message = message;
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
}
