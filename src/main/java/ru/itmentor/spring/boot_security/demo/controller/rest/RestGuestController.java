package ru.itmentor.spring.boot_security.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;


@RestController
@RequestMapping(value = "/api/authenticated/guest")
public class RestGuestController extends AbstractController {


    @Autowired
    protected RestGuestController(UserService userService,
                                  DtoUtils dtoUtils) {
        super(userService, dtoUtils);
    }



    @GetMapping("/guests")
    public ResponseEntity<String> showGuestInfo() {
        String message = "Пользователь: '"
                + getCurrentUser().getUserName()
                + "' успешно пошел в систему с БАЗОВЫМИ правами гостя.";
        return ResponseEntity.ok(message);
    }
}
