package com.example.todo.member.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session=request.getSession(false);
        if(session==null || session.getAttribute("loginUser")==null){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
