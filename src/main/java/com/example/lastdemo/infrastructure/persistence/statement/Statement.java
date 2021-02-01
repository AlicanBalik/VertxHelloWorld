package com.example.lastdemo.infrastructure.persistence.statement;

public interface Statement<T> {

  String sql();

  T params();
}
