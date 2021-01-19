package com.joakimatef.demo.repository.security;

import com.joakimatef.demo.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName (String customer);
}
