package com.example.todo.controller;

import com.example.todo.dto.ResponseDto;
import com.example.todo.dto.TodoDto;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getTodo(@AuthenticationPrincipal Long userId) {
        UserEntity user = userRepository.findByUserId(userId).get();
        List<TodoEntity> todoEntities = todoService.findByUser(user);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal Long userId, @RequestBody TodoDto todoDto) {
        log.info("todoDto : {}", todoDto);
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        todoEntity.setUser(userRepository.findByUserId(userId).get());
        todoEntity.setDone(false);
        List<TodoEntity> todoEntities = todoService.create(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal Long userId, @RequestBody TodoDto todoDto) {
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        todoEntity.setUser(userRepository.findByUserId(userId).get());
        log.info("[Delete] todoDto : {}", todoDto);
        List<TodoEntity> todoEntities = todoService.delete(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal Long userId, @RequestBody TodoDto todoDto) {
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        todoEntity.setUser(userRepository.findByUserId(userId).get());
        log.info("[Update] todoDto : {}", todoDto);
        List<TodoEntity> todoEntities = todoService.update(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

}
