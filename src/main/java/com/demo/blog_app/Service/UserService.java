package com.demo.blog_app.Service;

import com.demo.blog_app.Entity.User;
import com.demo.blog_app.Payload.SignUp;

public interface UserService {
    User saveUser(SignUp signUp);
}
