package com.joakimatef.demo.bootstrap;

import com.joakimatef.demo.domain.security.Authority;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.AuthorityRepository;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.security.PasswordEncoderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        loadSecurityData();
    }

    @Value("${PASS}")
    private String admin;
    @Value("${PASS2}")
    private String superAdmin;

    private void loadSecurityData() {


        //user auths
        Authority createUser = authorityRepository.save(Authority.builder().permission("user.create").build());
        Authority readUser = authorityRepository.save(Authority.builder().permission("user.read").build());
        Authority updateUser = authorityRepository.save(Authority.builder().permission("user.update").build());
        Authority deleteUser = authorityRepository.save(Authority.builder().permission("user.delete").build());

        Role superAdminRole = roleRepository.save(Role.builder().roleName("SUPERADMIN").build());
        Role adminRole = roleRepository.save(Role.builder().roleName("ADMIN").build());

        superAdminRole.setAuthorities(new HashSet<>(Set.of(createUser, readUser, updateUser, deleteUser)));
        adminRole.setAuthorities(new HashSet<>(Set.of(readUser, updateUser)));

        roleRepository.saveAll(Arrays.asList(superAdminRole, adminRole));

        userRepository.save(User.builder()
                .username("suAdmin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("wuru"))
                .role(superAdminRole)
                .build());

        userRepository.save(User.builder()
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build());


//        userRepository.save(User.builder()
//                .username("nySuAdmin")
//                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(superAdmin))
//                .role(superAdminRole)
//                .build());

        userRepository.save(User.builder()
                .username("nyAdmin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(admin))
                .role(adminRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }
}
