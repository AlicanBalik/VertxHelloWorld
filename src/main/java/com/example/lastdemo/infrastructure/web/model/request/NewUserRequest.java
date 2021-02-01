package com.example.lastdemo.infrastructure.web.model.request;

import com.example.lastdemo.domain.user.model.NewUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

//@JsonRootName("user")
@DataObject(generateConverter = true)
public final class NewUserRequest {

    private String email;
    private String password;

    // Used by objectMapper.
    public NewUserRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public NewUserRequest(JsonObject jsonObject) {
        NewUserRequestConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        NewUserRequestConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public NewUser toNewUser() {
        return new NewUser(this.email, this.password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
