package com.example.todo.todo.entity;

import com.example.todo.member.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id
    @GeneratedValue
    @Column(name="todo_id")
    private Long id;

    private String title;
    private Boolean state=false;
    private LocalDate start;
    private LocalDate finish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uid")
    private User user;

    public ToDo(String title, LocalDate finish) {
        this.title = title;
        this.start = LocalDate.now();
        this.finish = finish;
    }

    public void changeState() {
        this.state=!this.state;
    }

    public static ToDo createToDo(String title, LocalDate finish, User user){
        ToDo todo=new ToDo();

        todo.title=title;
        todo.start =LocalDate.now();
        todo.finish=finish;

        if(user!=null) todo.configUser(user);

        return todo;
    }

    private void configUser(User user){
        this.user=user;
        user.getToDoList().add(this);
    }

    public void update(String title, LocalDate finish){
        this.title=title;
        this.finish=finish;
    }
}
