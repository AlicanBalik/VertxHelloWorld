package com.example.lastdemo.infrastructure.web.model.response;

import com.example.lastdemo.domain.user.model.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonRootName("user")
@DataObject(generateConverter = true)
public final class UserResponse {

    private String email;

    public UserResponse(User user) {
        this.email = user.getEmail();
    }

    public UserResponse(JsonObject jsonObject) {
        UserResponseConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        UserResponseConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
