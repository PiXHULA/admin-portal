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

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailService jpaUserDetailService;
    private final JwtUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JpaUserDetailService jpaUserDetailService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jpaUserDetailService = jpaUserDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping({"/authenticate"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new IllegalStateException("Incorrect username or password", e);
        }

        final UserDetails userDetails = jpaUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}
