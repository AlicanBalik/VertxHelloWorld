package com.example.lastdemo.infrastructure.persistence.statement.impl;

import com.example.lastdemo.infrastructure.persistence.statement.Statement;
import io.vertx.core.json.JsonArray;

public class JsonArrayStatement implements Statement<JsonArray> {

    private final String sql;
    private final JsonArray params;

    public JsonArrayStatement(String sql, JsonArray params) {
        this.sql = sql;
        this.params = params;
    }

    @Override
    public String sql() {
        return sql;
    }

    @Override
    public JsonArray params() {
        return params;
    }
}
