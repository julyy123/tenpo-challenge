package com.juliansanchez.tenpochallenge.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserMapper(final PasswordEncoder passwordEncoder,
                      final RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Set.of(roleRepository.findRoleByName(ERole.ROLE_USER)));

        return user;
    }
}
