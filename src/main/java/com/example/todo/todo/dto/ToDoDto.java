package com.example.todo.todo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ToDoDto {

    private Long id;
    private String title;
    private Boolean state=false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finish;

    public ToDoDto(Long id, String title, Boolean state, LocalDate start, LocalDate finish) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.start = start;
        this.finish = finish;
    }
}
