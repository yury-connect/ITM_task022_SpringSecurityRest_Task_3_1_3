package ru.itmentor.spring.boot_security.demo.controller.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;


public abstract class AbstractController {

    protected UserService userService;
    protected DtoUtils dtoUtils;

    protected AbstractController(UserService userService,
                                 DtoUtils dtoUtils) {
        this.userService = userService;
        this.dtoUtils = dtoUtils;
    }


    // Получение залогиненного пользователя
    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userService.findUserByUsername(username); // Загрузка пользователя через UserService
        }
        return null;
    }
}
