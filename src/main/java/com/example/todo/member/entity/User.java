package com.example.todo.member.entity;

import com.example.todo.todo.entity.ToDo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name="uid")
    private Long uid;

    private String id;
    private String pw;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<ToDo> toDoList = new ArrayList<>();

    public User(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }
}
