package com.demo.blog_app.RestController;

import com.demo.blog_app.Entity.Role;
import com.demo.blog_app.Entity.User;
import com.demo.blog_app.Payload.JWTAuthResponse;
import com.demo.blog_app.Payload.SignIn;
import com.demo.blog_app.Payload.SignUp;
import com.demo.blog_app.Security.JwtTokenProvider;
import com.demo.blog_app.Service.RoleService;
import com.demo.blog_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/auth")
public class Auth {
        @Autowired
        private UserService userService;
        @Autowired
        private RoleService roleService;
        @Autowired
        private AuthenticationManager auth;
        @Autowired
        private JwtTokenProvider tokenProvider;
        //http://localhost:8080/blog/auth/setRole
    @PostMapping("/setRole")
    public ResponseEntity<?> setRole(@RequestBody Role role){
       Role role1 = roleService.saveRole(role);
        return new ResponseEntity<>(role1,HttpStatus.CREATED);
    }
    //http://localhost:8080/blog/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUp signUp){
          User user =  userService.saveUser(signUp);
          return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    //http://localhost:8080/blog/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignIn signIn){
        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUsernameOrEmail(),signIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

       String token = tokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new JWTAuthResponse(token),HttpStatus.OK);
    }

}
