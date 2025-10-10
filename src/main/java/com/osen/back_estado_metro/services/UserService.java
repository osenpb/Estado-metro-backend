package com.osen.back_estado_metro.services;

import com.osen.back_estado_metro.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User save(User user);
    User findById(Long id);
    Boolean update(User user);
    void deleteById(Long id);
    User findByEmail(String email);

}
