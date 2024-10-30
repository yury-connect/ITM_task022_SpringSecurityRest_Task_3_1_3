package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/authenticated/admin")
public class AdminController {

    private UserService userService;
    private UserUtilService userUtilService;



    @Autowired
    public AdminController(UserService service, UserUtilService userUtilService) {
        this.userService = service;
        this.userUtilService = userUtilService;
    }



    // Перенаправление на список всех пользователей (GET)
    @GetMapping()
    public String root() { // В этом случае - перенаправлю на страничку по умолчанию
        System.out.println("\n\tAdminController:root\n");
        return "redirect:/authenticated/admin/all";
    }

    // Генерация тестовых пользователей (GET)
    @GetMapping("/generate")
    public String generateUsers(@RequestParam(name = "count_generated_users", required = false, defaultValue = "0") Integer count) {
        userUtilService.generateTestData(count);
        return "redirect:/authenticated/admin/all";
    }

    // Отображение страницы создания пользователя (GET)
    @GetMapping("/create")
    public String showCreateUsersPage(Model model) {
        User defaultUser = userUtilService.generateNewUsers(-1).get(0);
        model.addAttribute("created_user", defaultUser);
        return "admin-pages/create_user_page";
    }

    // Создание нового пользователя (POST)
    @PostMapping("/create")
    public String createUser(@ModelAttribute("created_user") User user) {
        userService.createUser(user);
        return "redirect:/authenticated/admin/all";
    }



    // Отображение всех пользователей (GET)
    @GetMapping("/all")
    public String showAllUsersPage(Model model) {
        model.addAttribute("all_existing_users", userService.findAllUsers());
        return "admin-pages/all_users";
    }



    // Просмотр информации о конкретном пользователе (GET)
    @GetMapping("/view")
    public String showUserPage(@RequestParam("id_viewed_user") Integer id, Model model) {
        model.addAttribute("viewed_user", userService.findUserById(id));
        return "admin-pages/view_user_page";
    }



    // Отображение страницы редактирования пользователя (GET)
    @GetMapping("/edit")
    public String showEditUsersPage(@RequestParam("id_edited_user") Integer id, Model model) {
        model.addAttribute("edited_user", userService.findUserById(id));
        return "admin-pages/update_user_page";
    }

    // Обновление данных пользователя (POST с имитацией PUT)
    @PostMapping("/edit")
    public String editUsers(@ModelAttribute("edited_user") User user) {
        userService.updateUser(user);
        return "redirect:/authenticated/admin/all";
    }



    // Подтверждение удаления пользователя (GET)
    @GetMapping("/delete")
    public String showDeleteUserPage(@RequestParam("id_removed_user") Integer id, Model model) {
        System.out.println("ID for deletion: " + id); // Добавьте отладочный вывод                   *** *** *** *** ***   УДАЛИТЬ   *** *** *** ***
        model.addAttribute("removed_user", userService.findUserById(id));
        return "admin-pages/delete_user_page";
    }

    // Удаление пользователя (POST с имитацией DELETE)
    @PostMapping("/delete")
    public String deleteOneUser(@RequestParam(name = "id_removed_user") Integer id) {
        userService.deleteUserById(id);
        return "redirect:/authenticated/admin/all";
    }

    // Удаление всех пользователей (POST с имитацией DELETE)
    @PostMapping("/delete_all")
    public String deleteAllUsers() {
        List<User> deleteUsersList = new ArrayList<>(userService.findAllUsers());
        deleteUsersList.forEach(usr -> userService.deleteUserById(usr.getId()));
        return "redirect:/authenticated/admin/all";
    }
}
