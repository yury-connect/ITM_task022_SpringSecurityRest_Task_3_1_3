package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.*;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


// Перевод MVC-приложения на Spring Boot в RESTful API.
@RestController
@RequestMapping(value = "/api/authenticated/admin")
public class RestAdminController extends AbstractController {

    private DtoUtils dtoUtils;


    @Autowired
    public RestAdminController(UserService service,
                               DtoUtils dtoUtils) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.dtoUtils = dtoUtils;
    }



    // 1. Получение всех пользователей (GET)
    @Operation(summary = "Получение всех пользователей (GET)")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtoList = userService.findAllUsers().stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // 2. Получение конкретного пользователя по ID (GET)
    @Operation(summary = "Получение конкретного пользователя по ID (GET)")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> showUser(@PathVariable("id") Integer id) {
        UserDTO viewedUser = dtoUtils.convertToUserDto(userService.findUserById(id));
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER); // Скрываем пароль пользователя для безопасности
        return ResponseEntity.ok(viewedUser);
    }

    // 3. Создание одного пользователя (POST)
    @Operation(summary = "Создание одного нового пользователя (POST)")
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        User createdUser = userService.createUser(dtoUtils.convertToUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED) // используется HttpStatus.CREATED (201)
                .body(dtoUtils.convertToUserDto(createdUser));
    }

    // 4. Создание списка новых пользователей (POST)
    @Operation(summary = "Создание списка новых пользователей (POST)")
    @PostMapping("/users/batch")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<UserDTO> users) {
        List<User> createdUsers = users.stream()
                .map(dto -> userService.createUser(dtoUtils.convertToUser(dto)))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED) // используется HttpStatus.CREATED (201)
                .body(createdUsers.stream()
                        .map(dtoUtils::convertToUserDto)
                        .collect(Collectors.toList()));
    }

    // 5. Обновление данных пользователя (PUT)
    @Operation(summary = "Обновление данных пользователя (PUT)")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable("id") Integer id, @RequestBody UserDTO dto) {
        dto.setId(id); // Обеспечиваем совпадение ID в теле запроса и URL
        User updatedUser = userService.updateUser(dtoUtils.convertToUser(dto));
        return ResponseEntity.ok(dtoUtils.convertToUserDto(updatedUser));
    }

    // 6. Удаление пользователя (DELETE)
    @Operation(summary = "Удаление пользователя (DELETE)")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) { // ResponseEntity<Void> для удаления
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build(); // Возвращаем HttpStatus.NO_CONTENT (204)
    }

    // 7. Удаление всех пользователей (DELETE)
    @Operation(summary = "Удаление всех пользователей (DELETE)")
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAllUsers() { // ResponseEntity<Void> для удаления
        userService.findAllUsers().forEach(user -> userService.deleteUserById(user.getId()));
        return ResponseEntity.noContent().build(); // Возвращаем HttpStatus.NO_CONTENT (204)
    }
}



/*
В REST API принято использовать "snake-case" (нижний регистр с подчеркиваниями) для URL-адресов.
    Это обеспечивает лучшую читаемость и является более распространенной практикой в REST API.
 */
