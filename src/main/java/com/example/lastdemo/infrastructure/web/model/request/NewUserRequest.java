package com.example.lastdemo.infrastructure.web.model.request;

import com.example.lastdemo.domain.user.model.NewUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

//@JsonRootName("user")
@DataObject(generateConverter = true)
public class NewUserRequest implements Serializable {

    private String email;
    private String password;

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
        NewUser newUser = new NewUser();
        newUser.setEmail(this.email);
        newUser.setPassword(this.password);
        return newUser;
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
