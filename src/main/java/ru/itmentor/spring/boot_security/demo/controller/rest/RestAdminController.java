package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


// Перевод MVC-приложения на Spring Boot в RESTful API.
@RestController
@RequestMapping(value = "/api/authenticated/admin")
public class RestAdminController extends AbstractController {

    private UserUtilService userUtilService;


    @Autowired
    public RestAdminController(UserService service,
                               UserUtilService userUtilService) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
    }




    @Operation(summary = "Перенаправление на список всех пользователей (GET)")
    @GetMapping()
    public ResponseEntity<List<User>> root() { // В этом случае - перенаправлю на страничку по умолчанию
        System.out.println();
        return getAllUsers();
    }

    // ПЕРЕПИСАТЬ, тут должно пол логике возвращать созданных пользователей
    @Operation(summary = "Генерация тестовых пользователей (POST)")
    @GetMapping("/generate_users")
    public ResponseEntity<List<User>> generateUsers(@RequestParam(name = "count_generated_users", required = false, defaultValue = "0") Integer count) {
        List<User> generatedUsers = userUtilService.generateTestData(count)
                .stream()
                .peek(user -> user.setPassword(PASSWORD_PLACE_HOLDER))
                .collect(Collectors.toList());
        return ResponseEntity.ok(generatedUsers);
    }


    @Operation(summary = "Создание нового пользователя (POST)")
    @PostMapping("/create_user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Создание списка новых пользователей (POST)")
    @PostMapping("/create_any_users")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        List<User> createdUsers = new ArrayList<>();
//        List<User> createdUsers = Stream.generate(() -> generateUser(allExistingRoles));

        users.forEach(user -> createdUsers.add(userService.createUser(user)));
        return ResponseEntity.ok(createdUsers);
    }



    // Отображение всех пользователей (GET)
    @Operation(summary = "Получение всех пользователей (GET)")
    @GetMapping("/all_users")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("URL: /api/authenticated/admin/all_users" + "\tMethod: getAllUsers()");
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }







    @Operation(summary = "Просмотр информации о конкретном пользователе (GET)")
    @GetMapping("/view_user")
    public ResponseEntity<User> showUser(@RequestParam("id_viewed_user") Integer id) {
        User viewedUser = userService.findUserById(id);
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER);

        System.out.println("URL: /api/authenticated/admin/view" + "\tMethod: showUser()\nviewedUser = \t " + viewedUser);

        return ResponseEntity.ok(viewedUser);
    }



    @Operation(summary = "Обновление данных пользователя (POST с имитацией PUT)")
    @PostMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        System.out.println("URL: /api/authenticated/admin/edit" + "\tMethod: editUser()\n\tedited_user = \t " + user + "\n");
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }




    @Operation(summary = "Удаление пользователя (POST с имитацией DELETE)")
    @PostMapping("/delete")
    public ResponseEntity<User> deleteOneUser(@RequestParam(name = "id_removed_user") Integer id) {
        User deletedUser = userService.deleteUserById(id);
        deletedUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(deletedUser);
//        return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
    }

    @Operation(summary = "Удаление всех пользователей (POST с имитацией DELETE)")
    @PostMapping("/delete_all")
    public ResponseEntity<List<User>> deleteAllUsers() {
        List<User> deleteUsersList = new ArrayList<>(userService.findAllUsers());
        deleteUsersList.forEach(usr -> {
            userService.deleteUserById(usr.getId());
            usr.setPassword(PASSWORD_PLACE_HOLDER);
        });
        return ResponseEntity.ok(deleteUsersList);
    }



    @Operation(summary = "Просмотр информации о залогиненном пользователе (GET)")
    @GetMapping("/current_user")
    public ResponseEntity<User> showCurrentUser() {
        User currentUser = getCurrentUser(); // получаю (GET) залогиненного пользователя из общего суперкласса
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(currentUser);
    }
}

/*
В REST API принято использовать "snake-case" (нижний регистр с подчеркиваниями) для URL-адресов.
    Это обеспечивает лучшую читаемость и является более распространенной практикой в REST API.

 */
