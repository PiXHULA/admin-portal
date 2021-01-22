package com.joakimatef.demo.models;

/*
 * Output structure for the authenticate method
 */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationResponse {

    private final String jwt;
}
