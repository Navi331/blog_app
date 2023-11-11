package com.demo.blog_app.Service.impl;

import com.demo.blog_app.Entity.Role;
import com.demo.blog_app.Entity.User;
import com.demo.blog_app.Exception.MyCustomException;
import com.demo.blog_app.Payload.SignUp;
import com.demo.blog_app.Repository.RoleRepository;
import com.demo.blog_app.Repository.UserRepository;
import com.demo.blog_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
@Autowired
private UserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private RoleRepository roleRepository;
    @Override
    public User saveUser(SignUp signUp) {
        if (userRepository.existsByUsername(signUp.getUsername())){
            throw new MyCustomException("User already exists");
        } else if(userRepository.existsByEmail(signUp.getEmail())){
           throw new MyCustomException("Email already exists");
        }
        User user = new User();
        user.setId(signUp.getId());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
       Role r =roleRepository.findByName("ROLE_USER").get();
       user.setRole(Collections.singleton(r));
     return userRepository.save(user);
    }
}
