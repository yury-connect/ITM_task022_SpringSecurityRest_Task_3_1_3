package ru.itmentor.spring.boot_security.demo.model;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class AuthRequest {

//    @NotNull
    private String userName;

//    @NotNull
    private String password;
}