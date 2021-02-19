package com.joakimatef.demo.service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * PasswordEnconderFactory is our own implementation, we want to control which types of encoding that is
 * available to use
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */
public class PasswordEncoderFactory {

    /**
    * A static method providing the encryption.
    * No need to instantiate as it will only
    * encrypt the password before saving or retrieving from the database
    */
    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("bcrypt12", new BCryptPasswordEncoder(12));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    private PasswordEncoderFactory() {
    }
}
