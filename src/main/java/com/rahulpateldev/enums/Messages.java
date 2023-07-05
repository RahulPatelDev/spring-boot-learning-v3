package com.rahulpateldev.enums;

public enum Messages {
    USER_NOT_FOUND("Username does not exists: %s!!"),
    USER_ALREADY_EXIST("Username already exists: %s!!"),
    INVALID_CREDENTIALS("Invalid username and password!!"),
    RESOURCE_NOT_FOUND("%s Not Found!!");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
