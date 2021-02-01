package com.example.lastdemo.infrastructure.verticle;

import com.example.lastdemo.infrastructure.vertx.exception.mapper.BusinessExceptionMapper;
import com.example.lastdemo.infrastructure.web.exception.RequestValidationException;
import com.example.lastdemo.infrastructure.web.model.response.ErrorResponse;
import com.example.lastdemo.infrastructure.web.route.HttpRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Promise;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

// Cannot be immutable due to inheritance.
public class AbstractHttpVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpVerticle.class);

    private final BusinessExceptionMapper businessExceptionMapper;
    private final ObjectMapper objectMapper;

    @Value("${vertx.server.context_path}")
    private String contextPath;
    @Value("${vertx.server.port}")
    private int serverPort;

    public AbstractHttpVerticle(BusinessExceptionMapper businessExceptionMapper, ObjectMapper objectMapper) {
        this.businessExceptionMapper = businessExceptionMapper;
        this.objectMapper = objectMapper;
    }

    protected void createHttpServer(List<HttpRoute> httpRoutes, Promise<Void> startPromise) {
        vertx
                .createHttpServer()
                .requestHandler(subRouter(httpRoutes))
                .rxListen(serverPort)
                .subscribe(httpServer -> {
                    logger.info("HttpVerticle started on port " + serverPort);

                    startPromise.complete();
                }, startPromise::fail);
    }

    private void handlerRequestValidation(HttpServerResponse httpServerResponse,
                                          RequestValidationException requestValidationException) {

        httpServerResponse
                .setStatusCode(HttpResponseStatus.UNPROCESSABLE_ENTITY.code())
                .end(writeValueAsString(requestValidationException.getErrorResponse()));
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

    protected Router subRouter(List<HttpRoute> routers) {
        final Router baseRouter = Router.router(vertx);
        this.configApiErrorHandler(baseRouter);

        routers.forEach(router -> baseRouter.mountSubRouter(contextPath, router.configure(vertx)));

        return baseRouter;
    }

    private void configApiErrorHandler(Router baseRouter) {
        baseRouter
                .route()
                .failureHandler(
                        failureRoutingContext -> {
                            HttpServerResponse response = failureRoutingContext.response();

                            if (failureRoutingContext.failure() instanceof RequestValidationException) {
                                handlerRequestValidation(response, (RequestValidationException) failureRoutingContext.failure());
                            } else if (failureRoutingContext.failure() instanceof ServiceException) {
                                ServiceException serviceException = (ServiceException) failureRoutingContext.failure();

                                this.businessExceptionMapper.handle(serviceException, response);
                            } else {
                                response.end(writeValueAsString(new ErrorResponse(failureRoutingContext.failure().toString())));
                            }
                        });
    }

    protected String writeValueAsString(Object value) {
        String result;

        try {
            result = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }
}
