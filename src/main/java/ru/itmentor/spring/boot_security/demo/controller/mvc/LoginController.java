package ru.itmentor.spring.boot_security.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;

import java.util.*;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


@Controller
public class LoginController extends AbstractController {

    private UserUtilService userUtilService;
    private RoleService roleService;

    public LoginController(UserService service,
                           DtoUtils dtoUtils,
                           UserUtilService userUtilService,
                           RoleService roleService) {
        super(service, dtoUtils); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
        this.roleService = roleService;
    }

    @GetMapping("/loginURL")
    public String showLoginPage() {
        return "sevice-pages/login_page";
    }



    // Отображение страницы регистрации пользователя (GET)
    @GetMapping("/registrate")
    public String showRegistrationUsersPage(Model model) {
        User defaultUser = userUtilService.generateNewUsers(-1).get(0);
        defaultUser.setPassword(USER_PASSWORD_DEFAULT);
        model.addAttribute("created_user", defaultUser);

        return "sevice-pages/registration_page";
    }

    // Создание / регистрации нового пользователя (POST)
    @PostMapping("/registrate")
    public String registrationUser(@ModelAttribute("created_user") User user) {
        System.out.println("\n\n\tRegistration user: " + user + "\n\n");
        user.setRoles(Set.of(roleService.findRoleByName("ROLE_GUEST").get()));
        userService.createUser(user);
        return "redirect:/";
    }
}
