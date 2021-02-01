package com.example.lastdemo.infrastructure.persistence.statement;

import com.example.lastdemo.domain.user.model.User;
import io.vertx.core.json.JsonArray;

public interface UserStatements {

    Statement<JsonArray> create(User user);

    Statement<JsonArray> findByEmail(String email);

    Statement<JsonArray> countBy(String field, String value);

    Statement<JsonArray> findById(String id);
}
