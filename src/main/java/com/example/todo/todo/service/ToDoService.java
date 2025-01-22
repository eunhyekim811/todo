package com.example.todo.todo.service;

import com.example.todo.todo.entity.ToDo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ToDoService {

    Long save(ToDo todo);

    Optional<ToDo> findById(Long id);

    List<ToDo> findAll();

    void delete(ToDo todo);

    void update(Long id, String title, LocalDate finish);

    void changeStatus(Long id);

    List<ToDo> findToDoListByUserUidAndState(Long uid, Boolean state);
}
