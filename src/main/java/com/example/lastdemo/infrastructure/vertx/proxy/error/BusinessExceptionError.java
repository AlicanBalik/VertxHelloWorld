package com.example.lastdemo.infrastructure.vertx.proxy.error;

import com.example.lastdemo.domain.general.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public final class BusinessExceptionError<E extends BusinessException> implements Serializable {

    private final String className;
    private final E exception;

    public BusinessExceptionError(@JsonProperty("className") String className, @JsonProperty("exception") E exception) {
        this.className = className;
        this.exception = exception;
    }

    public String getClassName() {
        return className;
    }

    public E getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessExceptionError<?> businessExceptionError = (BusinessExceptionError<?>) o;
        return Objects.equals(className, businessExceptionError.className) && Objects.equals(exception, businessExceptionError.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, exception);
    }

    @Override
    public String toString() {
        return "Error{" +
                "className='" + className + '\'' +
                ", exception=" + exception +
                '}';
    }
}
