package com.osen.back_estado_metro.controllers;

import com.osen.back_estado_metro.dtos.LoginRequestDTO;
import com.osen.back_estado_metro.models.User;
import com.osen.back_estado_metro.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.email();
        String password = loginRequestDTO.password();

        User foundUser = userService.findByEmail(email); // podria ser optional en el service O.o
        String passwordUser = foundUser.getPassword();
        Boolean checker = passwordEncoder.matches(password, passwordUser);

        if(email.equals(foundUser.getEmail()) && checker) {
            return ResponseEntity.ok(foundUser.getId()); // xD
        }

        log.info("No se encontro al usuario D:");
        return ResponseEntity.badRequest().build();

    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<User> userList = userService.findAll();

        return ResponseEntity.ok(userList);
    }

    /**
     * Esto solo para el registro, luego lo puedes cambiar
     */
    @PostMapping("/new")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User foundUser = userService.findById(id);
        return ResponseEntity.ok(foundUser);
    }

}
