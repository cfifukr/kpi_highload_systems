package com.example.emoney.repositories;

import com.example.emoney.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}
