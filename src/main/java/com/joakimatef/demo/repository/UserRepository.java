package com.joakimatef.demo.repository;

import com.joakimatef.demo.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findUserById(Long adminId);
}
