package com.example.lastdemo.domain.user.model;

public interface ModelValidator {

  <T> void validate(T model);
}
