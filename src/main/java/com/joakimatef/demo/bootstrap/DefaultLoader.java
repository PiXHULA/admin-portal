package com.joakimatef.demo.bootstrap;

import com.joakimatef.demo.domain.security.Authority;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.AuthorityRepository;
import com.joakimatef.demo.repository.RoleRepository;
import com.joakimatef.demo.repository.UserRepository;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



@Slf4j
@RequiredArgsConstructor
@Configuration
public class DefaultLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if(roleRepository.findAll().size() < 1)
            loadSecurityData();
    }

    private void loadSecurityData() {


        //user auths
        Authority createUser = authorityRepository.save(Authority.builder().permission("user.create").build());
        Authority readUser = authorityRepository.save(Authority.builder().permission("user.read").build());
        Authority updateUser = authorityRepository.save(Authority.builder().permission("user.update").build());
        Authority deleteUser = authorityRepository.save(Authority.builder().permission("user.delete").build());

        Authority readUserAdmin = authorityRepository.save(Authority.builder().permission("user.admin.read").build());
        Authority updateUserAdmin = authorityRepository.save(Authority.builder().permission("user.admin.update").build());

        Role superAdminRole = roleRepository.save(Role.builder().roleName("SUPERADMIN").build());
        Role adminRole = roleRepository.save(Role.builder().roleName("ADMIN").build());

        superAdminRole.setAuthorities(new HashSet<>(Set.of(createUser, readUser, updateUser, deleteUser)));
        adminRole.setAuthorities(new HashSet<>(Set.of(readUserAdmin, updateUserAdmin)));

        roleRepository.saveAll(Arrays.asList(superAdminRole, adminRole));

        userRepository.save(User.builder()
                .username("suAdmin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("wuru"))
                .role(superAdminRole)
                .build());

        userRepository.save(User.builder()
                .username("supAdmin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("zuru"))
                .role(superAdminRole)
                .build());

        userRepository.save(User.builder()
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }
}
