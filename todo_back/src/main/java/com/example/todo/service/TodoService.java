package com.example.todo.service;

import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
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
        return todoRepository.findByUser(todoEntity.getUser());
    }

    public List<TodoEntity> delete(TodoEntity todoEntity) {
        todoRepository.delete(todoEntity);
        return todoRepository.findByUser(todoEntity.getUser());
    }

    public List<TodoEntity> update(TodoEntity todoEntity) {
        List<TodoEntity> original = todoRepository.findByUser(todoEntity.getUser());
        for (TodoEntity todo : original) {
            if (todo.getTodoId() == todoEntity.getTodoId()) {
                todo.setTitle(todoEntity.getTitle());
                todo.setDone(todoEntity.getDone());
            }
            todoRepository.save(todo);
        }
        return original;
    }

    public List<TodoEntity> findByUser(UserEntity user) {
        return todoRepository.findByUser(user);
    }
}
