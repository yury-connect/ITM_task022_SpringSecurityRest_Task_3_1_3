package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/authenticated/user")
public class UserController {

    @GetMapping("")
    public String showUserInfoPage(Model model) {
        System.out.println("\n\tUserController:showUserInfoPage\n");
        return "users_pages/user_info_page";
    }
}
