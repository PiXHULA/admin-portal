package com.joakimatef.demo.service.security;


import com.joakimatef.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserDetailsService used in order to
 * retrieve an authenticated user from database
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {

    /*
    * Repository used for CRUD operations for users
    * */
    private final UserRepository userRepository;

    /**
    *
    * @param username for the admin trying to login
    * @return Userdetails containing information about the login
    *
    **/

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Getting User Info via JPA");

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name: " + username +" not found"));
    }
}
