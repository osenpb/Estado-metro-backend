package com.osen.back_estado_metro.services;

import com.osen.back_estado_metro.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();
    Role save(Role role);
    Role findById(Long id);
    Boolean update(Role role);

}
