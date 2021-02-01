package com.example.lastdemo.infrastructure.vertx.proxy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AsyncResult;
import io.vertx.serviceproxy.ServiceException;

public class AbstractOperations {

  private final ObjectMapper objectMapper;

  public AbstractOperations(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  <T> AsyncResult<T> error(Throwable throwable) {
    String error;
    try {

//      if (throwable instanceof BusinessException) {
//        BusinessException businessException = (BusinessException) throwable;
//        error =
//            objectMapper.writeValueAsString(
//                new Error<>(businessException.getClass().getName(), businessException));
//      } else {
        error = objectMapper.writeValueAsString(throwable);
//      }

    } catch (JsonProcessingException ex) {
      error = ex.getMessage();
    }
    return ServiceException.fail(1, error);
  }
}
