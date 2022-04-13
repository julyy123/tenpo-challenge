package com.juliansanchez.tenpochallenge.security.authentication;

import lombok.Data;

/**
 * Authentication request class
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
