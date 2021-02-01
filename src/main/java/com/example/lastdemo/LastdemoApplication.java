package com.example.lastdemo;

import com.example.lastdemo.infrastructure.verticle.HttpVerticle;
import com.example.lastdemo.infrastructure.vertx.configuration.VertxConfiguration;
import io.vertx.reactivex.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties(VertxConfiguration.class)
public class LastdemoApplication {

    private final Vertx vertx;
    private final HttpVerticle httpVerticle;

    public LastdemoApplication(Vertx vertx, HttpVerticle httpVerticle) {
        this.vertx = vertx;
        this.httpVerticle = httpVerticle;
    }

    public static void main(String[] args) {
        SpringApplication.run(LastdemoApplication.class, args);
    }

    @PostConstruct
    public void deployMainVerticle() {
        vertx.rxDeployVerticle(httpVerticle).blockingGet();
    }
}
