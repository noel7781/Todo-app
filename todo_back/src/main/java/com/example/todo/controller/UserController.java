package com.example.todo.controller;

import com.example.todo.dto.ResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.UserEntity;
import com.example.todo.security.TokenProvider;
import com.example.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        try {
            log.info("signup userDto : {}", userDto);
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();

            UserEntity registeredUser = userService.create(user);
            UserDto result = UserDto.builder()
                    .email(registeredUser.getEmail())
                    .name(registeredUser.getName())
                    .build();

            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDto userDto) {
        Optional<UserEntity> user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword(), passwordEncoder);
        if(user.isPresent()) {
            UserEntity userEntity = user.get();
            String token = tokenProvider.createToken(userEntity);
            UserDto result = UserDto.builder()
                    .email(userEntity.getEmail())
                    .userId(userEntity.getUserId())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(result);
        } else {
            ResponseDto responseDto = ResponseDto.builder().error("Login Failed").build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }


}
