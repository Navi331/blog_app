package com.demo.blog_app.Repository;

import com.demo.blog_app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   Optional <User> findByUsernameOrEmail(String userNameOrEmail, String usernameOrEmail);
   boolean existsByUsername(String username);
   boolean existsByEmail(String email);

}
