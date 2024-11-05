package ru.itmentor.spring.boot_security.demo.model;

import lombok.Data;


@Data
public class AuthRequest {
    private String userName;
    private String password;
}