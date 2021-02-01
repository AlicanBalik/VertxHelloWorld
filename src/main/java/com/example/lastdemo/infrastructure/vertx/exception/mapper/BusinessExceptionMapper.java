package com.example.lastdemo.infrastructure.vertx.exception.mapper;

import com.example.lastdemo.domain.general.exception.BusinessException;
import com.example.lastdemo.domain.user.exception.EmailAlreadyExistsException;
import com.example.lastdemo.domain.user.exception.UserNotFoundException;
import com.example.lastdemo.infrastructure.vertx.proxy.error.BusinessExceptionError;
import com.example.lastdemo.infrastructure.web.model.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.serviceproxy.ServiceException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BusinessExceptionMapper {

    private final Map<String, BusinessExceptionHandler> exceptionMapper;
    private final ObjectMapper objectMapper;

    public BusinessExceptionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.exceptionMapper = this.configureExceptionMapper();
    }

    private Map<String, BusinessExceptionHandler> configureExceptionMapper() {
        Map<String, BusinessExceptionHandler> handlerMap = new HashMap<>();

        handlerMap.put(EmailAlreadyExistsException.class.getName(), this.conflict());
        handlerMap.put(UserNotFoundException.class.getName(), this.notFound());

        return handlerMap;
    }

    private BusinessExceptionHandler notFound() {
        return exceptionHandler(
                HttpResponseStatus.NOT_FOUND.reasonPhrase(), HttpResponseStatus.NOT_FOUND.code());
    }

    private BusinessExceptionHandler conflict() {
        return exceptionHandler(
                HttpResponseStatus.CONFLICT.reasonPhrase(), HttpResponseStatus.CONFLICT.code());
    }

    private BusinessExceptionHandler exceptionHandler(String message, int httpStatusCode) {
        return (httpServerResponse, businessException) -> {
            ErrorResponse errorResponse = new ErrorResponse(message);

            if (businessException.haveMessages()) {
                errorResponse = new ErrorResponse(businessException.getMessages());
            }

            errorResponse(httpServerResponse, errorResponse, httpStatusCode);
        };
    }

    private void errorResponse(HttpServerResponse httpServerResponse, ErrorResponse errorResponse, int httpStatusCode) {
        try {
            httpServerResponse
                    .setStatusCode(httpStatusCode)
                    .end(objectMapper.writeValueAsString(errorResponse));
        } catch (JsonProcessingException e) {
            httpServerResponse.end(e.getMessage());
        }
    }

    public void handle(ServiceException serviceException, HttpServerResponse httpServerResponse) {
        try {
            BusinessExceptionError error = objectMapper.readValue(serviceException.getMessage(), BusinessExceptionError.class);

            this.exceptionMapper.get(error.getClassName()).handler(httpServerResponse, error.getException());
        } catch (Exception e) {
            e.printStackTrace();
            httpServerResponse.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
        }
    }

    private interface BusinessExceptionHandler {
        void handler(HttpServerResponse httpServerResponse, BusinessException businessException);
    }
}
