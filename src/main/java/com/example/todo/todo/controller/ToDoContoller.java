package com.example.todo.todo.controller;

import com.example.todo.member.entity.User;
import com.example.todo.member.service.UserService;
import com.example.todo.todo.dto.ToDoDto;
import com.example.todo.todo.entity.ToDo;
import com.example.todo.todo.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ToDoContoller {

    private final ToDoService toDoService;
    private final UserService userService;

    //미완료된 todo 리스트 뷰에 전달
    @ModelAttribute("toDoDtos")
    public List<ToDoDto> toDoDtos(HttpServletRequest request){
        return getToDoDtos(getSessionUser(request), false);
    }

    //완료된 todo 리스트 뷰에 전달
    @ModelAttribute("completedDtos")
    public List<ToDoDto> completedDtos(HttpServletRequest request){
        return getToDoDtos(getSessionUser(request), true);
    }

    private List<ToDoDto> getToDoDtos(User loginUser, boolean state) {
        List<ToDo> list=toDoService.findToDoListByUserUidAndState(loginUser.getUid(), state);
        return list.stream().map(toDo ->
                new ToDoDto(toDo.getId(), toDo.getTitle(), toDo.getState(), toDo.getStart(), toDo.getFinish()))
                .collect(Collectors.toList());
    }

    private static User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loginUser");
    }

    @GetMapping("/todo")
    public String todo(@ModelAttribute("toDoDto") ToDoDto toDoDto){
        return "/todo/main";
    }

    @GetMapping("/todo/add")
    public String add(@ModelAttribute("toDoDto") ToDoDto toDoDto){
        return "/todo/add";
    }

    @GetMapping("todo/update/{id}")
    public String edit(@PathVariable Long id, Model model){
        if(id!=null){
            Optional<ToDo> todo=toDoService.findById(id);
            todo.ifPresent(toDoDto -> model.addAttribute("toDoDto", toDoDto));
        } else{
            return "redirect:/todo";
        }

        return "/todo/edit";
    }

    @PostMapping("/todo/add")
    public ResponseEntity<String> addTodo(@ModelAttribute("toDoDto") ToDoDto toDoDto, HttpServletRequest request){

        Optional<User> findUser=userService.findById(getSessionUser(request).getUid());
        Optional<ToDo> createToDo=findUser.map(user -> ToDo.createToDo(
                toDoDto.getTitle(), toDoDto.getFinish(), user
        ));
        createToDo.ifPresent(toDo -> toDoService.save(toDo));

//        return "redirect:/todo";
        return ResponseEntity.status(HttpStatus.CREATED).body("ToDo 추가 완료");
    }

    @PostMapping("/todo/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @ModelAttribute("toDoDto") ToDoDto toDoDto){
        if(id!=null){
            toDoService.update(id, toDoDto.getTitle(), toDoDto.getFinish());
            return ResponseEntity.status(HttpStatus.OK).body("ToDo 수정 완료");
        }

//        return "redirect:/todo";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
    }

    @PostMapping("/todo/change/{id}")
    public ResponseEntity<String> change(@PathVariable Long id){
        if(id!=null){
            toDoService.changeStatus(id);
            return ResponseEntity.status(HttpStatus.OK).body("ToDo 상태 변경 완료");
        }

//        return "redirect:/todo";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
    }

    @PostMapping("/todo/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(id!=null){
            toDoService.findById(id).ifPresent(toDo -> toDoService.delete(toDo));
            return ResponseEntity.status(HttpStatus.OK).body("ToDo 삭제 완료");
        }

//        return "redirect:/todo";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
    }
}
