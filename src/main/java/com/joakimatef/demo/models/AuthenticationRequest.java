package com.joakimatef.demo.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
* Input structure for the authenticate method
*
*/
@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationRequest {

    private String username;
    private String password;

}
