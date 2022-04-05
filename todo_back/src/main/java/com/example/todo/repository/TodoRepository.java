package com.example.todo.repository;

import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
//    public TodoEntity save(TodoEntity todoEntity);
    List<TodoEntity> findByUser(UserEntity user);
}
