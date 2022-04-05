package com.example.todo.service;

import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null) {
            throw new IllegalArgumentException("UserEntity is null or email is null");
        }

        String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            log.warn("Email already exists: {}", email);
            throw new IllegalArgumentException("Email already exists");
        }

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getByCredentials(String email, String password, PasswordEncoder encoder) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            if(encoder.matches(password, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.ofNullable(null);
    }

}
