package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.handlerException.UserNotFoundException;
import com.osen.back_estado_metro.models.User;
import com.osen.back_estado_metro.repositories.UserRepository;
import com.osen.back_estado_metro.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {

        String email= user.getEmail();
        if(userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Este correo ya esta registrado");
        }

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(STR."Usuario con id: '\{id}' no encontrado"));
    }

    @Override
    public Boolean update(User user) {

        if(userRepository.existsById(user.getId())){
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void deleteById(Long id) { // corregir aqui
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(STR."Usuario con email '\{email}' no encontrado"));
    }
}
