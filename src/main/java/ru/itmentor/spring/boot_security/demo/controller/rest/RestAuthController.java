package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.*;
import ru.itmentor.spring.boot_security.demo.service.*;
import ru.itmentor.spring.boot_security.demo.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api/auth")
public class RestAuthController extends AbstractController {

    private final DtoUtils dtoUtils;
    private RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public RestAuthController(UserService service,
                              RoleService roleService,
                              AuthenticationManager authenticationManager,
                              JwtUtil jwtUtil, DtoUtils dtoUtils,
                              PasswordEncoder passwordEncoder) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.dtoUtils = dtoUtils;
        this.passwordEncoder = passwordEncoder;
    }



    @Operation(summary = "Получение токена/ залогинивание/ аутентификация (POST)")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> logIn(@RequestBody AuthRequest authRequest) {
        System.out.println("\n\n\n\tПопытка залогиниться, пользователь: " + authRequest.getUserName() + "\n");

        try { // Попытка аутентификации пользователя
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(), authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) { // Неверные учетные данные
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Неверные учетные данные"));
        }

        // Загрузка данных пользователя
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUserName());

        // Генерация JWT токена
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        System.out.println("\tЗалогинился пользователь: " + authRequest.getUserName() + "\n\n\n");

        // Возвращаем токен
        AuthResponse response = new AuthResponse(jwt);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Разлогинивание текущего пользователя (POST)")
    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletRequest request) {
        try {
            // Здесь можно добавить дополнительную логику аннулирования токена,
            // например, добавить токен в черный список.

            // Используем встроенный метод сервлета для разлогинивания пользователя (если используем сессионное хранение)
            request.logout();
            return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500 Internal Server Error
        }
    }


    @Operation(summary = "Регистрация нового пользователя (POST)")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO registerUserDTO) {
        // Создаем роли для нового пользователя
        Set<Role> createdUserRoles = Stream.generate(
                () -> roleService.findRoleByName("ROLE_GUEST").get()
        ).collect(Collectors.toSet());

        // Преобразуем DTO в объект User
        User user = dtoUtils.convertToUser(registerUserDTO);
        user.setRoles(createdUserRoles);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        // Сохраняем пользователя в базе данных
        User createdUser = userService.createUser(user);

        // Преобразуем сохраненного пользователя обратно в UserDTO
        UserDTO createdUserDTO = dtoUtils.convertToUserDto(createdUser);

        // Возвращаем ResponseEntity с кодом 201 и информацией о созданном пользователе
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }
}
