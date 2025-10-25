package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.models.Role;
import com.osen.back_estado_metro.repositories.RoleRepository;
import com.osen.back_estado_metro.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encontro el rol"));
    }

    @Override
    public Boolean update(Role role) {
        if(roleRepository.existsById(role.getId())){
            roleRepository.save(role);
            return true;
        }
        return false;
    }
}
