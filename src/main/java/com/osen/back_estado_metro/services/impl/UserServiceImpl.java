package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.handlerException.UserNotFoundException;
import com.osen.back_estado_metro.models.Role;
import com.osen.back_estado_metro.models.User;
import com.osen.back_estado_metro.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
        Role roleUser = roleRepository.findById(1L).orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setRole(roleUser);
        user.setPassword(passwordEncoded);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Usuario con id: " + id + "no encontrado"));
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
                .orElseThrow(() -> new UserNotFoundException("Usuario con email " + email + "no encontrado"));
    }
}
