package com.example.todo.todo.service;

import com.example.todo.todo.entity.ToDo;
import com.example.todo.todo.repo.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService{

    private final ToDoRepository repository;

    @Override
    @Transactional
    public Long save(ToDo todo) {
        return repository.save(todo).getId();
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ToDo> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void delete(ToDo todo) {
        repository.delete(todo);
    }

    @Override
    @Transactional
    public void update(Long id, String title, LocalDate finish) {
        Optional<ToDo> todo=repository.findById(id);
        todo.ifPresent(t -> t.update(title, finish));
    }

    @Override
    @Transactional
    public void changeStatus(Long id) {
        Optional<ToDo> findToDo=repository.findById(id);
        findToDo.ifPresent(toDo -> toDo.changeState());
    }

    @Override
    public List<ToDo> findToDoListByUserUidAndState(Long uid, Boolean state) {
        return repository.findToDoListByUserUidAndState(uid, state);
    }
}
