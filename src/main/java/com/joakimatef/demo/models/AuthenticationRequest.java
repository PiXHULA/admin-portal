package com.joakimatef.demo.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * AuthenticationResponse - POJO class for the input structure for the authenticate method
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */
@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationRequest {

    private String username;
    private String password;

}
