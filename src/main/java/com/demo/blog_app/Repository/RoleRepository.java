package com.demo.blog_app.Repository;

import com.demo.blog_app.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
