package com.juliansanchez.tenpochallenge.security.authentication;

import com.juliansanchez.tenpochallenge.security.jwt.JwtTokenUtil;
import com.juliansanchez.tenpochallenge.security.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Authentication controller class to authenticate user
 */
@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final CustomUserDetailsService customUserDetailsService,
                                    final JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Login endpoint to authenticate user
     *
     * @param authenticationRequest {@link AuthenticationRequest}
     * @return {@link ResponseEntity}
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(final @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}