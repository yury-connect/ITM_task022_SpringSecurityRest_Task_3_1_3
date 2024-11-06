package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;

import java.util.List;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


@RestController
@RequestMapping(value = "/api/authenticated/services")
public class RestServicesController extends AbstractController {

    private UserUtilService userUtilService;


    protected RestServicesController(UserService userService,
                                     UserUtilService userUtilService,
                                     DtoUtils dtoUtils) {
        super(userService, dtoUtils);
        this.userUtilService = userUtilService;
    }



    @Operation(summary = "Генерация тестовых пользователей (POST)")
    @PostMapping("/generate_test_users")
    public ResponseEntity<List<UserDTO>> generateUsers(@RequestParam(name = "count", defaultValue = "0") Integer count) {
        return ResponseEntity.ok(userUtilService.generateTestData(count));
    }



    @Operation(summary = "Просмотр информации о залогиненном пользователе (GET)")
    @GetMapping("/current_user")
    public ResponseEntity<UserDTO> showCurrentUser() {
        User currentUser = getCurrentUser();
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(dtoUtils.convertToUserDto(currentUser));
    }
}
