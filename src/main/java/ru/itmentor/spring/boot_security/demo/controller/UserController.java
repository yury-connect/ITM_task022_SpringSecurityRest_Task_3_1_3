package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.UserUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
@RequestMapping(value = "/users")
public class UserController {

    private UserService service;



    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }



    // Перенаправление на список всех пользователей (GET)
    @GetMapping()
    public String getAllUsers(Model model) { // В этом случае - перенаправлю на страничку по умолчанию
        return "redirect:/users/all";
    }

    // Генерация тестовых пользователей (GET)
    @GetMapping("/generate")
    public String generateUsers(@RequestParam(name = "count_generated_users", required = false, defaultValue = "1") Integer count) {
        UserUtils.generateUsers(count).forEach(service::createUser);
        return "redirect:/users/all";
    }

    // Отображение страницы создания пользователя (GET)
    @GetMapping("/create")
    public String showCreateUsersPage(Model model) {
        User defaultUser = UserUtils.generateUser();

        defaultUser.setId(-1);
        defaultUser.setUserName("Default_UserName_" + defaultUser.getUserName());
        defaultUser.setEmail("Default_Email_" + defaultUser.getEmail());
        defaultUser.setFullName("Default_UserName_" + defaultUser.getFullName());
        defaultUser.setDateBirth(new Date(System.currentTimeMillis()));
        defaultUser.setAddress("Default_Address_" + defaultUser.getAddress());

        model.addAttribute("created_user", defaultUser);
        return "create_user_page";
    }

    // Создание нового пользователя (POST)
    @PostMapping("/create")
    public String createUser(@ModelAttribute("created_user") User user) {
        service.createUser(user);
        return "redirect:/users/all";
    }



    // Отображение всех пользователей (GET)
    @GetMapping("/all")
    public String showAllPage(Model model) {
        model.addAttribute("all_existing_users", service.findAllUsers());
        return "all_users";
    }



    // Просмотр информации о конкретном пользователе (GET)
    @GetMapping("/view")
    public String showUserPage(@RequestParam("id_viewed_user") Integer id, Model model) {
        model.addAttribute("viewed_user", service.findUserById(id));
        return "view_user_page";
    }



    // Отображение страницы редактирования пользователя (GET)
    @GetMapping("/edit")
    public String showEditUsersPage(@RequestParam("id_edited_user") Integer id, Model model) {
        model.addAttribute("edited_user", service.findUserById(id));
        return "update_user_page";
    }

    // Обновление данных пользователя (POST с имитацией PUT)
    @PostMapping("/edit")
    public String editUsers(@ModelAttribute("edited_user") User user) {
        service.updateUser(user);
        return "redirect:/users/all";
    }



    // Подтверждение удаления пользователя (GET)
    @GetMapping("/delete")
    public String showDeleteUsersPage(@RequestParam("id_removed_user") Integer id, Model model) {
        System.out.println("ID for deletion: " + id); // Добавьте отладочный вывод                   *** *** *** *** ***   УДАЛИТЬ   *** *** *** ***
        model.addAttribute("removed_user", service.findUserById(id));
        return "delete_user_page";
    }

    // Удаление пользователя (POST с имитацией DELETE)
    @PostMapping("/delete")
    public String deleteUsers(@RequestParam(name = "id_removed_user") Integer id) {
        service.deleteUserById(id);
        return "redirect:/users/all";
    }

    // Удаление всех пользователей (POST с имитацией DELETE)
    @PostMapping("/delete_all")
    public String deleteAllUsers() {
        List<User> deleteUsersList = new ArrayList<>(service.findAllUsers());
        deleteUsersList.forEach(usr -> service.deleteUserById(usr.getId()));
        return "redirect:/users/all";
    }
}
