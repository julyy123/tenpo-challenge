package com.juliansanchez.tenpochallenge.security.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
