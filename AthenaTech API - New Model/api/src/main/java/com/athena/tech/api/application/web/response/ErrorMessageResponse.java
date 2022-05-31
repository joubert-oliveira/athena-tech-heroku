package com.athena.tech.api.application.web.response;

public class ErrorMessageResponse {
    private String errorType;
    private String exceptionMessage;

    public ErrorMessageResponse(String errorType, String exceptionMessage) {
        this.errorType = errorType;
        this.exceptionMessage = exceptionMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
