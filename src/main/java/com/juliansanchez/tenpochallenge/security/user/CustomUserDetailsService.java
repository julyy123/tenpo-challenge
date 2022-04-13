package com.juliansanchez.tenpochallenge.security.user;

import com.juliansanchez.tenpochallenge.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("Finding users {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
