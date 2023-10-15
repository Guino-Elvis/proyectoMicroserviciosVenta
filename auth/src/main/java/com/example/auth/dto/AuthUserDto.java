package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto {
    private String role;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
