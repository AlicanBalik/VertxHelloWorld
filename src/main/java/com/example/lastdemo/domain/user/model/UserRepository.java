package com.example.lastdemo.domain.user.model;

import io.reactivex.Single;

import java.util.Optional;

public interface UserRepository {

    Single<User> store(User user);

    Single<Optional<User>> findUserByEmail(String email);

    Single<Long> countByEmail(String email);

    Single<Optional<User>> findById(String id);
}
