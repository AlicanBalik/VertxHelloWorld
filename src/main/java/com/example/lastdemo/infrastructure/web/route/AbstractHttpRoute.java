package com.example.lastdemo.infrastructure.web.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public abstract class AbstractHttpRoute implements HttpRoute {

    private final ObjectMapper objectMapper;

    public AbstractHttpRoute(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <T> T getBody(RoutingContext routingContext, Class<T> clazz) {
        T result;
        try {
            result = objectMapper.readValue(routingContext.getBodyAsString(), clazz);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    protected <T> void response(RoutingContext routingContext, int statusCode, T response) {
        try {
            routingContext
                    .response()
                    .setStatusCode(statusCode)
                    .end(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            routingContext.fail(e);
        }
    }

    protected <T> Handler<AsyncResult<T>> responseOrFail(
            RoutingContext routingContext, int statusCode) {
        return asyncResult -> {
            if (asyncResult.succeeded()) {
                T result = asyncResult.result();
                response(routingContext, statusCode, result);
            } else {
                routingContext.fail(asyncResult.cause());
            }
        };
    }
}
