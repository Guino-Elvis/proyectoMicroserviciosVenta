package com.example.auth.dto;

import com.example.auth.entity.AuthUser;

import lombok.Data;

@Data
public class CreateUserResponse {
    private String message;
    private AuthUser user;
}
