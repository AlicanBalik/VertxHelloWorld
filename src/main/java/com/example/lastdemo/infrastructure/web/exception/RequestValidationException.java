package com.example.lastdemo.infrastructure.web.exception;

import com.example.lastdemo.infrastructure.web.model.response.ErrorResponse;

public final class RequestValidationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public RequestValidationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
