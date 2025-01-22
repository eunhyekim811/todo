package com.example.todo.member.controller;

import com.example.todo.member.dto.LoginDto;
import com.example.todo.member.entity.User;
import com.example.todo.member.service.LoginService;
import com.example.todo.member.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto, HttpServletRequest request){
        HttpSession session= request.getSession(false);
        return session!=null && session.getAttribute("loginUser")!=null ? "redirect:/" : "/login/form";
//        return "/form";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        HttpSession session= request.getSession(false);
        if(session!=null && session.getAttribute("loginUser")!=null)
            session.invalidate();

//        return "redirect:/";
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/todo") String redirectURL,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()) {
//            return "/login/form";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("입력값 오류");
        }

        Optional<User> loginUser=loginService.login(loginDto.getId(), loginDto.getPw());

        if(loginUser.isEmpty()){
            bindingResult.reject("loginFail", "id pw 에러");
//            return "/login/form";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 실패");
        }

//        loginUser.ifPresent(user -> {
//            HttpSession session=request.getSession();
//            session.setAttribute("loginUser", user);
//        });
        HttpSession session=request.getSession();
        session.setAttribute("loginUser", loginUser.get());

//        return "redirect:/todo";
//        return "redirect:" + redirectURL;
        return ResponseEntity.status(HttpStatus.OK)
                .body("로그인 성공");
    }
}
