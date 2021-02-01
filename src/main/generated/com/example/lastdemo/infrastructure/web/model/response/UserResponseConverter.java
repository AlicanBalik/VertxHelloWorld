package com.example.lastdemo.infrastructure.web.model.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link com.example.lastdemo.infrastructure.web.model.response.UserResponse}.
 * NOTE: This class has been automatically generated from the {@link com.example.lastdemo.infrastructure.web.model.response.UserResponse} original class using Vert.x codegen.
 */
public class UserResponseConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, UserResponse obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "email":
          if (member.getValue() instanceof String) {
            obj.setEmail((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(UserResponse obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(UserResponse obj, java.util.Map<String, Object> json) {
    if (obj.getEmail() != null) {
      json.put("email", obj.getEmail());
    }
  }
}
