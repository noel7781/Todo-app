package com.example.todo.dto;

import com.example.todo.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private Long todoId;
    private String title;
    private Boolean done;

    public TodoDto(TodoEntity todoEntity) {
        this.todoId = todoEntity.getTodoId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.getDone();
    }

    public static TodoEntity toEntity(TodoDto todoDto) {
        return TodoEntity.builder().todoId(todoDto.todoId).title(todoDto.title).done(todoDto.done).build();
    }
}
