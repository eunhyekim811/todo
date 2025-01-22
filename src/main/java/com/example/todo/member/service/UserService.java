package com.example.todo.member.service;

import com.example.todo.member.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Long save(User user);

    List<User> findAll();

    Optional<User> findById(Long id);
}
