package com.demo.blog_app.Service.impl;

import com.demo.blog_app.Entity.Role;
import com.demo.blog_app.Exception.MyCustomException;
import com.demo.blog_app.Repository.RoleRepository;
import com.demo.blog_app.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role saveRole(Role role) {
        if(roleRepository.existsByName(role.getName())){
            throw new MyCustomException("Role is already exists");
        }
      return   roleRepository.save(role);
    }
}
