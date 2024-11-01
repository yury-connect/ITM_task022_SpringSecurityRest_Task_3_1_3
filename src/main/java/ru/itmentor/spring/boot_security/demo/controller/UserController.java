package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


@Controller
@RequestMapping(value = "/authenticated/user")
public class UserController extends AbstractController {

    @Autowired
    protected UserController(UserService userService) {
        super(userService);
    }



    @GetMapping()
    public String showUserInfoPage(Model model) {
        User currentUser = getCurrentUser(); // получаю (GET) залогиненного пользователя из общего суперкласса
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);

        model.addAttribute("viewed_user", currentUser);
        return "users_pages/user_info_page";
    }
}
