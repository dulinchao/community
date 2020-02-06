package com.community.my.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUSTION_NOT_FOUND("此文章不存在");
    private  String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
