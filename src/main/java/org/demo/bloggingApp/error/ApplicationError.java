package org.demo.bloggingApp.error;

import lombok.Getter;

@Getter
public enum ApplicationError {

    USER_NOT_FOUND("User not found", 400),
    USER_ALREADY_EXISTS("User already exists", 400),
    INVALID_PASSWORD("Invalid password", 400),
    INTERNAL_SERVER_ERROR("Internal server error", 500);

    private final String message;
    private final int statusCode;

    ApplicationError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
