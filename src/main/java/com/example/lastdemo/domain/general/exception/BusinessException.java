package com.example.lastdemo.domain.general.exception;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BusinessException extends RuntimeException {

    private List<String> messages;

    public BusinessException() {
        this.messages = new LinkedList<>();
    }

    public BusinessException(String message) {
        this();
        this.messages.add(message);
    }

    public BusinessException(List<String> messages) {
        this.messages = messages;
    }

    public boolean haveMessages() {
        return Objects.nonNull(this.getMessages()) && this.getMessages().size() > 0;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessException that = (BusinessException) o;
        return Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "messages=" + messages +
                '}';
    }
}
