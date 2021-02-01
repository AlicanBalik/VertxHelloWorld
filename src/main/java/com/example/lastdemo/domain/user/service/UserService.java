package com.example.lastdemo.domain.user.service;

import com.example.lastdemo.domain.user.model.NewUser;
import com.example.lastdemo.domain.user.model.User;
import io.reactivex.Single;

public interface UserService {

    Single<User> create(NewUser newUser);
}
