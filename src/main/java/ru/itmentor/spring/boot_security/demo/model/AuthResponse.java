package ru.itmentor.spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthResponse {

    private final String jwtToken;
}