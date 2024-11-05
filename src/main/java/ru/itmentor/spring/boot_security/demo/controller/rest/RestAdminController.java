package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Operation(summary = "Перенаправление на список всех пользователей (GET)")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> root() { // В этом случае - перенаправлю на страничку по умолчанию
        return getAllUsers();
    }


    @Operation(summary = "Создание нового пользователя (POST)")
    @PostMapping("/create_users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        User createdUser = userService.createUser(dtoUtils.convertToUser(dto));
        return ResponseEntity.ok(dtoUtils.convertToUserDto(createdUser));
    }

    @Operation(summary = "Создание списка новых пользователей (POST)")
    @PostMapping("/create_any_users")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<UserDTO> users) {
        List<User> createdUsers = users.stream()
                .map(dto -> userService.createUser(dtoUtils.convertToUser(dto)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(createdUsers.stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList()));
    }



    @Operation(summary = "Получение всех пользователей (GET)")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtoList = userService.findAllUsers().stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }






    @Operation(summary = "Просмотр информации о конкретном пользователе (GET)")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> showUser(@PathVariable("id") Integer id) {
        UserDTO viewedUser = dtoUtils.convertToUserDto(userService.findUserById(id));
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(viewedUser);
    }




    @Operation(summary = "Обновление данных пользователя (PUT)")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable("id") Integer id, @RequestBody UserDTO dto) {
        dto.setId(id); // Убедимся, что ID совпадает с URL
        User updatedUser = userService.updateUser(dtoUtils.convertToUser(dto));
        return ResponseEntity.ok(dtoUtils.convertToUserDto(updatedUser));
    }





    @Operation(summary = "Удаление пользователя (DELETE)")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Integer id) {
        UserDTO deletedUser = dtoUtils.convertToUserDto(userService.deleteUserById(id));
        return ResponseEntity.ok(deletedUser);
    }


    @Operation(summary = "Удаление всех пользователей (DELETE)")
    @DeleteMapping("/users")
    public ResponseEntity<List<UserDTO>> deleteAllUsers() {
        List<UserDTO> deletedUsersList = userService.findAllUsers().stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        deletedUsersList.forEach(dto -> userService.deleteUserById(dto.getId()));
        return ResponseEntity.ok(deletedUsersList);
    }
}



/*
В REST API принято использовать "snake-case" (нижний регистр с подчеркиваниями) для URL-адресов.
    Это обеспечивает лучшую читаемость и является более распространенной практикой в REST API.

 */
