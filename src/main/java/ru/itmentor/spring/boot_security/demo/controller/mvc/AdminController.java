package ru.itmentor.spring.boot_security.demo.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;

import java.util.ArrayList;
import java.util.List;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;
import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


@Controller
@RequestMapping(value = "/authenticated/admin")
public class AdminController extends AbstractController {

    private UserUtilService userUtilService;
    private RoleService roleService;



    @Autowired
    public AdminController(UserService service, UserUtilService userUtilService, RoleService roleService) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
        this.roleService = roleService;
    }



    // Перенаправление на список всех пользователей (GET)
    @GetMapping()
    public String root() { // В этом случае - перенаправлю на страничку по умолчанию
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
        defaultUser.setPassword(USER_PASSWORD_DEFAULT);
        model.addAttribute("created_user", defaultUser);

        model.addAttribute("all_existing_roles", roleService.findAllRoles());

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
        User viewedUser = userService.findUserById(id);
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER);
        model.addAttribute("viewed_user", viewedUser);
        return "admin-pages/view_user_page";
    }



    // Отображение страницы редактирования пользователя (GET)
    @GetMapping("/edit")
    public String showEditUsersPage(@RequestParam("id_edited_user") Integer id, Model model) {
        User editedUser = userService.findUserById(id);
        editedUser.setPassword(PASSWORD_PLACE_HOLDER);
        model.addAttribute("edited_user", editedUser);
        model.addAttribute("all_existing_roles", roleService.findAllRoles());
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



    // Просмотр информации о залогиненном пользователе (GET)
    @GetMapping("/current_user")
    public String showCurrentUserPage(Model model) {
        User currentUser = getCurrentUser(); // получаю (GET) залогиненного пользователя из общего суперкласса
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);

        model.addAttribute("viewed_user", currentUser);
        return "users_pages/user_info_page";
    }
}
