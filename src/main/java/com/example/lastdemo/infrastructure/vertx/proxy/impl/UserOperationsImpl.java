package com.example.lastdemo.infrastructure.vertx.proxy.impl;

import com.example.lastdemo.domain.user.service.UserService;
import com.example.lastdemo.infrastructure.vertx.proxy.UserOperations;
import com.example.lastdemo.infrastructure.web.model.request.NewUserRequest;
import com.example.lastdemo.infrastructure.web.model.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class UserOperationsImpl extends AbstractOperations implements UserOperations {

    private final UserService userService;

    public UserOperationsImpl(ObjectMapper objectMapper, UserService userService) {
        super(objectMapper);
        this.userService = userService;
    }

    @Override
    public void create(NewUserRequest newUserRequest, Handler<AsyncResult<UserResponse>> handler) {
        userService
                .create(newUserRequest.toNewUser())
                .subscribe(
                        user -> handler.handle(Future.succeededFuture(new UserResponse(user))),
                        throwable -> handler.handle(error(throwable)));
    }
}
