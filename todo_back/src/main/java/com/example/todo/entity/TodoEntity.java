package com.example.todo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long todoId;
    private String title;
    private Boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
