/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.lastdemo.infrastructure.vertx.proxy.reactivex;

import io.vertx.reactivex.RxHelper;
import io.vertx.reactivex.ObservableHelper;
import io.vertx.reactivex.FlowableHelper;
import io.vertx.reactivex.impl.AsyncResultMaybe;
import io.vertx.reactivex.impl.AsyncResultSingle;
import io.vertx.reactivex.impl.AsyncResultCompletable;
import io.vertx.reactivex.WriteStreamObserver;
import io.vertx.reactivex.WriteStreamSubscriber;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.lang.rx.RxGen;
import io.vertx.lang.rx.TypeArg;
import io.vertx.lang.rx.MappingIterator;


@RxGen(com.example.lastdemo.infrastructure.vertx.proxy.UserOperations.class)
public class UserOperations {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserOperations that = (UserOperations) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final TypeArg<UserOperations> __TYPE_ARG = new TypeArg<>(    obj -> new UserOperations((com.example.lastdemo.infrastructure.vertx.proxy.UserOperations) obj),
    UserOperations::getDelegate
  );

  private final com.example.lastdemo.infrastructure.vertx.proxy.UserOperations delegate;
  
  public UserOperations(com.example.lastdemo.infrastructure.vertx.proxy.UserOperations delegate) {
    this.delegate = delegate;
  }

  public UserOperations(Object delegate) {
    this.delegate = (com.example.lastdemo.infrastructure.vertx.proxy.UserOperations)delegate;
  }

  public com.example.lastdemo.infrastructure.vertx.proxy.UserOperations getDelegate() {
    return delegate;
  }

  public void create(com.example.lastdemo.infrastructure.web.model.request.NewUserRequest newUserRequest, Handler<AsyncResult<com.example.lastdemo.infrastructure.web.model.response.UserResponse>> handler) { 
    delegate.create(newUserRequest, handler);
  }

  public void create(com.example.lastdemo.infrastructure.web.model.request.NewUserRequest newUserRequest) {
    create(newUserRequest, ar -> { });
  }

  public io.reactivex.Single<com.example.lastdemo.infrastructure.web.model.response.UserResponse> rxCreate(com.example.lastdemo.infrastructure.web.model.request.NewUserRequest newUserRequest) { 
    return AsyncResultSingle.toSingle($handler -> {
      create(newUserRequest, $handler);
    });
  }

  public static final String SERVICE_ADDRESS = com.example.lastdemo.infrastructure.vertx.proxy.UserOperations.SERVICE_ADDRESS;
  public static UserOperations newInstance(com.example.lastdemo.infrastructure.vertx.proxy.UserOperations arg) {
    return arg != null ? new UserOperations(arg) : null;
  }

}
