package com.example.lastdemo.infrastructure.persistence.statement.impl;

import com.example.lastdemo.domain.user.model.User;
import com.example.lastdemo.infrastructure.persistence.statement.Statement;
import com.example.lastdemo.infrastructure.persistence.statement.UserStatements;
import io.vertx.core.json.JsonArray;
import org.springframework.stereotype.Component;

@Component
public class UserStatementsImpl extends AbstractStatements implements UserStatements {

    @Override
    public Statement<JsonArray> create(User user) {

        String sql =
                "INSERT INTO USERS (ID, EMAIL, PASSWORD) VALUES (?, ?, ?)";

        JsonArray params =
                new JsonArray()
                        .add(user.getId())
                        .add(user.getEmail())
                        .add(user.getPassword());

        return new JsonArrayStatement(sql, params);
    }

    @Override
    public Statement<JsonArray> findById(String id) {

        String sql = "SELECT * FROM USERS WHERE ID = ?";

        JsonArray params = new JsonArray().add(id);

        return new JsonArrayStatement(sql, params);
    }

    @Override
    public Statement<JsonArray> countBy(String field, String value) {

        String sql =
                String.format("SELECT COUNT(*) FROM USERS WHERE UPPER(%s) = ?", field.toUpperCase());

        JsonArray params = new JsonArray().add(value.toUpperCase().trim());

        return new JsonArrayStatement(sql, params);
    }

    @Override
    public Statement<JsonArray> findByEmail(String email) {

        String sql = "SELECT * FROM USERS WHERE UPPER(EMAIL) = ?";

        JsonArray params = new JsonArray().add(email.toUpperCase().trim());

        return new JsonArrayStatement(sql, params);
    }
}
