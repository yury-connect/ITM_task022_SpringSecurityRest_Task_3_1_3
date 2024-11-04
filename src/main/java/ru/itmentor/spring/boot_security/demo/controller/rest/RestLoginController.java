package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.model.AuthRequest;
import ru.itmentor.spring.boot_security.demo.model.AuthResponse;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;
import ru.itmentor.spring.boot_security.demo.util.JwtUtil;

import java.util.Set;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


// Перевод MVC-приложения на Spring Boot в RESTful API.

/**
 * Контроллер залогиниваться/ регистрации/ разлогиниваться
 */
@RestController
@RequestMapping(value = "/api/auth")
public class RestLoginController extends AbstractController {

    private UserUtilService userUtilService;
    private RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public RestLoginController(UserService service,
                               UserUtilService userUtilService,
                               RoleService roleService,
                               AuthenticationManager authenticationManager,
                               JwtUtil jwtUtil) {
        super(service); //  прокидываю UserService в общий суперкласс) {
        this.userUtilService = userUtilService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Operation(summary = "Получение токена (POST)")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println("\n\n\t" + authRequest.getUserName() + "\n\n");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
        }

        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }


//    // Отображение страницы регистрации пользователя (GET)
//    @GetMapping("/registrate")
//    public String showRegistrationUsersPage(Model model) {
//        User defaultUser = userUtilService.generateNewUsers(-1).get(0);
//        defaultUser.setPassword(USER_PASSWORD_DEFAULT);
//        model.addAttribute("created_user", defaultUser);
//
//        return "sevice-pages/registration_page";
//    }
//
//    // Создание / регистрации нового пользователя (POST)
//    @PostMapping("/registrate")
//    public String registrationUser(@ModelAttribute("created_user") User user) {
//        System.out.println("\n\n\tRegistration user: " + user + "\n\n");
//        user.setRoles(Set.of(roleService.findRoleByName("ROLE_GUEST").get()));
//        userService.createUser(user);
//        return "redirect:/";
//    }
}
