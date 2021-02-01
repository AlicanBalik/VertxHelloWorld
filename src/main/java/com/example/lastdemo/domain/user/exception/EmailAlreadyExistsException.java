package com.example.lastdemo.domain.user.exception;

import com.example.lastdemo.domain.general.exception.BusinessException;

public final class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
