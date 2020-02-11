package com.community.my.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
