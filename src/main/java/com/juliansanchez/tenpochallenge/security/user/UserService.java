package com.juliansanchez.tenpochallenge.security.user;

import com.juliansanchez.tenpochallenge.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;

    @Autowired
    public UserService (final UserMapper userMapper,
                        final UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public void register(final UserDto userDto) throws InvalidInputException {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new InvalidInputException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidInputException("Error: Email is already in use!");
        }
        User user = userMapper.convertToEntity(userDto);

        userRepository.save(user);
    }

    public String getLoggedUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }
}
