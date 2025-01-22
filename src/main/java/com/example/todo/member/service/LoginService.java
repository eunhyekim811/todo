package com.example.todo.member.service;

import com.example.todo.member.entity.User;

import java.util.Optional;

public interface LoginService {

    Optional<User> login(String id, String pw);
}
