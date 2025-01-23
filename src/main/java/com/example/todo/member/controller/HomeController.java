package com.example.todo.member.controller;

import com.example.todo.member.dto.UserDto;
import com.example.todo.member.entity.User;
import com.example.todo.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/joinform")
    public String joinForm(@ModelAttribute UserDto userDto){
        return "join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute UserDto userDto, BindingResult bindingResult){
        if(!userDto.getPw().equals(userDto.getCheckPw()))
            bindingResult.rejectValue("checkPassword", "", "비밀번호가 일치하지 않습니다.");

        if(bindingResult.hasErrors()) {
            return "join";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("입력 데이터 오류");
        }

        User user=new User(userDto.getId(), userDto.getPw(), userDto.getName());
        if(userService.save(user)==null){
            bindingResult.rejectValue("Id", "duplication", "이미 존재하는 ID입니다.");
            return "join";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("중복 ID");
        }

        return "redirect:/";
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("회원가입 완료");
    }
}
