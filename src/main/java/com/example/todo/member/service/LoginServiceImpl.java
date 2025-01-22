package com.example.todo.member.service;

import com.example.todo.member.entity.User;
import com.example.todo.member.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository repository;

    @Override
    public Optional<User> login(String id, String pw) {
        Optional<User> findUser=repository.findById(id);
        return findUser.filter(user -> user.getPw().equals(pw));
    }
}
