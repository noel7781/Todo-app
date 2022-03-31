package com.example.todo.controller;

import com.example.todo.dto.ResponseDto;
import com.example.todo.dto.TodoDto;
import com.example.todo.entity.TodoEntity;
import com.example.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<?> getTodo() {
        List<TodoEntity> todoEntities = todoService.findAll();
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto) {
        log.info("todoDto : {}", todoDto);
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        todoEntity.setTodoId(null);
        todoEntity.setDone(false);
        List<TodoEntity> todoEntities = todoService.create(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto todoDto) {
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        log.info("[Delete] todoDto : {}", todoDto);
        List<TodoEntity> todoEntities = todoService.delete(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto todoDto) {
        TodoEntity todoEntity = TodoDto.toEntity(todoDto);
        log.info("[Update] todoDto : {}", todoDto);
        List<TodoEntity> todoEntities = todoService.update(todoEntity);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(response);
    }

}
