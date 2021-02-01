package com.example.lastdemo.infrastructure.verticle;

import com.example.lastdemo.infrastructure.vertx.exception.mapper.BusinessExceptionMapper;
import com.example.lastdemo.infrastructure.web.route.HttpRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Promise;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class HttpVerticle extends AbstractHttpVerticle {

    private final List<HttpRoute> httpRoutes;

    public HttpVerticle(BusinessExceptionMapper businessExceptionMapper, ObjectMapper wrapUnwrapRootValueObjectMapper, List<HttpRoute> httpRoutes) {
        super(businessExceptionMapper, wrapUnwrapRootValueObjectMapper);
        this.httpRoutes = httpRoutes;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        createHttpServer(this.httpRoutes, startPromise);
    }
}
