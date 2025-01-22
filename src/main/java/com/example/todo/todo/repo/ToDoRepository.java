package com.example.todo.todo.repo;

import com.example.todo.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

//    List<ToDo> findToDoListByUidAndState(Long uid, boolean state);
    List<ToDo> findToDoListByUserUidAndState(Long uid, Boolean state);
}
