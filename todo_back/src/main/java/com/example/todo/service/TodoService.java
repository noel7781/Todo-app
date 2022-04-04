package com.example.todo.service;

import com.example.todo.entity.TodoEntity;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    public List<TodoEntity> findAll() {
        return todoRepository.findAll();
    }
    public List<TodoEntity> create(TodoEntity todoEntity) {
        todoRepository.save(todoEntity);
        return todoRepository.findAll();
    }

    public List<TodoEntity> delete(TodoEntity todoEntity) {
        todoRepository.delete(todoEntity);
        return todoRepository.findAll();
    }

    public List<TodoEntity> update(TodoEntity todoEntity) {
        List<TodoEntity> original = todoRepository.findAll();
        for (TodoEntity todo : original) {
            if (todo.getTodoId() == todoEntity.getTodoId()) {
                todo.setTitle(todoEntity.getTitle());
                todo.setDone(todoEntity.getDone());
            }
            todoRepository.save(todo);
        }
        return original;
    }
}
