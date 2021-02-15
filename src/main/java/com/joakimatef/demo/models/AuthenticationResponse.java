package com.joakimatef.demo.models;

/*
 * Output structure for the authenticate method
 */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * AuthenticationResponse - POJO class for the response jwt
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */
@Getter
@RequiredArgsConstructor
public class AuthenticationResponse {
    private final String jwt;
}
