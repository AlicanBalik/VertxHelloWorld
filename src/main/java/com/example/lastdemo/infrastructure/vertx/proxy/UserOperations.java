package com.example.lastdemo.infrastructure.vertx.proxy;

import com.example.lastdemo.infrastructure.web.model.request.NewUserRequest;
import com.example.lastdemo.infrastructure.web.model.response.UserResponse;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@ProxyGen
@VertxGen
public interface UserOperations {

    String SERVICE_ADDRESS = "user-service-event-bus";

    void create(NewUserRequest newUserRequest, Handler<AsyncResult<UserResponse>> handler);
}
