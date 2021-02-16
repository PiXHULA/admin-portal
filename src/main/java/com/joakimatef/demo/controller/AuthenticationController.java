package com.joakimatef.demo.controller;

import com.joakimatef.demo.models.AuthenticationRequest;
import com.joakimatef.demo.models.AuthenticationResponse;
import com.joakimatef.demo.service.security.JpaUserDetailService;
import com.joakimatef.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController used for posting an authentication request and retrieving a json web token
 * @author Joakim Önnhage briceno
 * @since 12.01.2021
 */
@RestController
public class AuthenticationController {

    /**
     * Used for authenticate method and checks is the user isAuthenticated before creating a jwt
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Service handling crud operations for Users
     */
    private final JpaUserDetailService jpaUserDetailService;
    /**
     * JwtUtil used to generate and claim JWT
     */
    private final JwtUtil jwtTokenUtil;


    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JpaUserDetailService jpaUserDetailService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jpaUserDetailService = jpaUserDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Endpoint for login
     * @param authenticationRequest Structure for login in with a json object
     * @return ResponseEntity with a new AuthenticationResponse Structure for jwt
     */
    @PostMapping({"/authenticate"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = jpaUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}
