package com.example.emoney.dtos;


public class AuthenticationResponseDto {
    String token;

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
