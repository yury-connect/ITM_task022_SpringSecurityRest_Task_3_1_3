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

    private UserUtilService userUtilService;
    private DtoUtils dtoUtils;


    @Autowired
    public RestAdminController(UserService service,
                               UserUtilService userUtilService,
                               DtoUtils dtoUtils) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
        this.dtoUtils = dtoUtils;
    }




    @Operation(summary = "Перенаправление на список всех пользователей (GET)")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> root() { // В этом случае - перенаправлю на страничку по умолчанию
        System.out.println();
        return getAllUsers();
    }

    @Operation(summary = "Генерация тестовых пользователей (POST)")
    @PostMapping("/generate_users")
    public ResponseEntity<List<UserDTO>> generateUsers(@RequestParam(name = "count", defaultValue = "0") Integer count) {
        List<UserDTO> generatedUsersDTO = userUtilService.generateTestData(count).stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(generatedUsersDTO);
    }


    @Operation(summary = "Создание нового пользователя (POST)")
    @PostMapping("/create_user")
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
    @GetMapping("/all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtoList = userService.findAllUsers().stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }






    /*@Operation(summary = "Просмотр информации о конкретном пользователе (GET)")
    @GetMapping("/view_user")
    public ResponseEntity<UserDTO> showUser(@RequestParam("id_viewed_user") Integer id) {
        UserDTO viewedUser = dtoUtils.convertToUserDto(userService.findUserById(id));
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER);

        System.out.println("URL: /api/authenticated/admin/view" + "\tMethod: showUser()\nUserDTO = \t " + viewedUser);

        return ResponseEntity.ok(viewedUser);
    }*/
    // *****************************************   ok   *****************************************
    @Operation(summary = "Просмотр информации о конкретном пользователе (GET)")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> showUser(@PathVariable("id") Integer id) {
        UserDTO viewedUser = dtoUtils.convertToUserDto(userService.findUserById(id));
        viewedUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(viewedUser);
    }



    /*@Operation(summary = "Обновление данных пользователя (POST с имитацией PUT)")
    @PostMapping("/edit")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO dto) {

        System.out.println("URL: /api/authenticated/admin/edit" + "\tMethod: editUser()\n\tUserDTO = \t " + dto + "\n");

        UserDTO updatedUserDTO = dtoUtils.convertToUserDto(userService.updateUser(dtoUtils.convertToUser(dto)));

        System.out.println("URL: /api/authenticated/admin/edit" + "\tMethod: editUser()\n\tUser = \t " + updatedUserDTO + "\n");

        return ResponseEntity.ok(updatedUserDTO);
    }*/
    // *****************************************   ok   *****************************************
    @Operation(summary = "Обновление данных пользователя (PUT)")
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable("id") Integer id, @RequestBody UserDTO dto) {
        dto.setId(id); // Убедимся, что ID совпадает с URL
        User updatedUser = userService.updateUser(dtoUtils.convertToUser(dto));
        return ResponseEntity.ok(dtoUtils.convertToUserDto(updatedUser));
    }




    /*@Operation(summary = "Удаление пользователя (POST с имитацией DELETE)")
    @PostMapping("/delete")
    public ResponseEntity<UserDTO> deleteOneUser(@RequestParam(name = "id_removed_user") Integer id) {
        UserDTO deletedUser = dtoUtils.convertToUserDto(userService.deleteUserById(id));
        return ResponseEntity.ok(deletedUser);
    }*/
    // *****************************************   ok   *****************************************
    @Operation(summary = "Удаление пользователя (DELETE)")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Integer id) {
        UserDTO deletedUser = dtoUtils.convertToUserDto(userService.deleteUserById(id));
        return ResponseEntity.ok(deletedUser);
    }

    /*@Operation(summary = "Удаление всех пользователей (POST с имитацией DELETE)")
    @PostMapping("/delete_all")
    public ResponseEntity<List<UserDTO>> deleteAllUsers() {
        List<UserDTO> deletedUsersList = userService.findAllUsers().stream() // Получаем список всех пользователей
                .map(dtoUtils::convertToUserDto) // Сначала конвертируем в DTO
                .collect(Collectors.toList());
        deletedUsersList.forEach(dto -> userService.deleteUserById(dto.getId())); // Удаляем всех пользователей по списку ID
        return ResponseEntity.ok(deletedUsersList);
    }*/
    // *****************************************   ok   *****************************************
    @Operation(summary = "Удаление всех пользователей (DELETE)")
    @DeleteMapping("/all_users")
    public ResponseEntity<List<UserDTO>> deleteAllUsers() {
        List<UserDTO> deletedUsersList = userService.findAllUsers().stream()
                .map(dtoUtils::convertToUserDto)
                .collect(Collectors.toList());
        deletedUsersList.forEach(dto -> userService.deleteUserById(dto.getId()));
        return ResponseEntity.ok(deletedUsersList);
    }




    @Operation(summary = "Просмотр информации о залогиненном пользователе (GET)")
    @GetMapping("/current_user")
    public ResponseEntity<UserDTO> showCurrentUser() {
        User currentUser = getCurrentUser();
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(dtoUtils.convertToUserDto(currentUser));
    }
}



/*
В REST API принято использовать "snake-case" (нижний регистр с подчеркиваниями) для URL-адресов.
    Это обеспечивает лучшую читаемость и является более распространенной практикой в REST API.

 */
