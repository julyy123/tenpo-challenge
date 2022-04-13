package com.juliansanchez.tenpochallenge.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Authentication response class
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private final String token;
}
