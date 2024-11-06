package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


@RestController
@RequestMapping(value = "/api/authenticated/user")
public class RestUserController extends AbstractController {

    private UserUtilService userUtilService;


    @Autowired
    protected RestUserController(UserService userService,
                                 UserUtilService userUtilService,
                                 DtoUtils dtoUtils) {
        super(userService, dtoUtils);
        this.userUtilService = userUtilService;
    }



    // Получение информации о себе-же /залогиненном пользователе (GET)
    @Operation(summary = "Получение информации о себе-же /залогиненном пользователе (GET)")
    @GetMapping("/users")
    public ResponseEntity<UserDTO> showUserInfo(Model model) {
        User currentUser = getCurrentUser();
        currentUser.setPassword(PASSWORD_PLACE_HOLDER);
        return ResponseEntity.ok(dtoUtils.convertToUserDto(currentUser));
    }
}
