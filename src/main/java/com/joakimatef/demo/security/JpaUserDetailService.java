package com.joakimatef.demo.security;


import com.joakimatef.demo.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Getting User Info via JPA"); //TODO : CHECK WHY IT SHOWS TWICE WHEN CALLING ONCE

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name: " + username +" not found"));
    }
}
