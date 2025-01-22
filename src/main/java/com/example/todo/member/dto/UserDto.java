package com.example.todo.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String id;
    private String pw;
    private String checkPw;
    private String name;
}
