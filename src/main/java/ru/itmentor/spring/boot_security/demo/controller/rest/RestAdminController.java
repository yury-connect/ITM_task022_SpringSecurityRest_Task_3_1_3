package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.controller.mvc.AbstractController;
import ru.itmentor.spring.boot_security.demo.model.AuthRequest;
import ru.itmentor.spring.boot_security.demo.model.AuthResponse;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.*;
import ru.itmentor.spring.boot_security.demo.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;
import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


// Перевод MVC-приложения на Spring Boot в RESTful API.
@RestController
@RequestMapping(value = "/api/authenticated/admin")
public class RestAdminController extends AbstractController {

    private UserUtilService userUtilService;
    private RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @Autowired
    public RestAdminController(UserService service,
                               UserUtilService userUtilService,
                               RoleService roleService,
                               AuthenticationManager authenticationManager,
                               JwtUtil jwtUtil) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Operation(summary = "Получение токена (POST)")
    @PostMapping("/authenticate")
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



    @Operation(summary = "Перенаправление на список всех пользователей (GET)")
    @GetMapping()
    public String root() { // В этом случае - перенаправлю на страничку по умолчанию
        return "redirect:/authenticated/admin/all_users";
    }

    // ПЕРЕПИСАТЬ, тут должно пол логике возвращать созданных пользователей
    @Operation(summary = "Генерация тестовых пользователей (POST)")
    @PostMapping("/generate_users")
    public ResponseEntity<Integer> generateUsers(@RequestParam(name = "count_generated_users", required = false, defaultValue = "0") Integer count) {
        userUtilService.generateTestData(count);
        return ResponseEntity.ok(count);
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
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
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

/*
В REST API принято использовать "snake-case" (нижний регистр с подчеркиваниями) для URL-адресов.
    Это обеспечивает лучшую читаемость и является более распространенной практикой в REST API.

 */
