package com.joakimatef.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderFactory {

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("bcrypt12", new BCryptPasswordEncoder(12)); //In case we want to further increase the encryption
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    //Private constructor inorder to not instantiate this class and only use the static method
    private PasswordEncoderFactory() {
    }
}
