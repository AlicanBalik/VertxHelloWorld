package com.example.lastdemo.infrastructure.configuration;

import com.example.lastdemo.application.UserServiceImpl;
import com.example.lastdemo.domain.user.model.ModelValidator;
import com.example.lastdemo.domain.user.model.UserRepository;
import com.example.lastdemo.domain.user.service.UserService;
import com.example.lastdemo.infrastructure.vertx.configuration.VertxConfiguration;
import com.example.lastdemo.infrastructure.vertx.proxy.UserOperations;
import com.example.lastdemo.infrastructure.vertx.proxy.impl.UserOperationsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfiguration {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public JDBCClient jdbcClient(Vertx vertx, VertxConfiguration vertxConfiguration) {
        VertxConfiguration.Database database = vertxConfiguration.getDatabase();
        JsonObject dataBaseConfig = new JsonObject();
        dataBaseConfig.put("url", database.getUrl());
        dataBaseConfig.put("driver_class", database.getDriverClass());
        dataBaseConfig.put("max_pool_size", database.getMaxPoolSize());
        dataBaseConfig.put("user", database.getUser());
        dataBaseConfig.put("password", database.getPassword());

        return JDBCClient.createShared(vertx, dataBaseConfig);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    @Bean
    public UserService userService(UserRepository userRepository, ModelValidator modelValidator) {
        return new UserServiceImpl(userRepository, modelValidator);
    }

    @Bean
    public UserOperations userOperations(Vertx vertx, UserService userService, ObjectMapper objectMapper) {
        return registerServiceAndCreateProxy(vertx, UserOperations.class, UserOperations.SERVICE_ADDRESS,
                new UserOperationsImpl(objectMapper, userService));
    }

    private <T> T registerServiceAndCreateProxy(
            Vertx vertx, Class<T> clazz, String address, T instance) {
        this.registerProxy(vertx, clazz, address, instance);

        return createProxy(vertx, clazz, address);
    }

    private <T> void registerProxy(Vertx vertx, Class<T> clazz, String address, T instance) {
        new ServiceBinder(vertx.getDelegate()).setAddress(address).register(clazz, instance);
    }

    private <T> T createProxy(Vertx vertx, Class<T> clazz, String address) {
        return new ServiceProxyBuilder(vertx.getDelegate()).setAddress(address).build(clazz);
    }
}
