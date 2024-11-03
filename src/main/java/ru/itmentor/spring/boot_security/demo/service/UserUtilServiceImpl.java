package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;
import ru.itmentor.spring.boot_security.demo.util.UserGenerator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;
import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


@Service
public class UserUtilServiceImpl implements UserUtilService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGenerator userGenerator;


    @Autowired
    public UserUtilServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder,
                               UserGenerator userGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGenerator = userGenerator;
    }



    /*
     * Генерация случайных пользователей
     * Если count < 1, то создается один пользователь с значениями по умолчанию
     * Если count >= 1, то генерируется указанное количество пользователей с фейковыми данными
     */
    @Override
    public List<User> generateNewUsers(int count) {
        List<Role> allExistingRoles = roleRepository.findAll();

        List<User> userList;
        Set<Role> rolesDefault = allExistingRoles.stream()
                .filter(role -> "ROLE_GUEST".equals(role.getName()))
                .collect(Collectors.toSet());

        if (count < 1) {
            userList = new ArrayList<>();
            userList.add(User.builder()
                    .id(-1) // при создании ставим заведомо несуществующий id
                    .userName("userLogin")
                    .password(USER_PASSWORD_DEFAULT)
                    .email("userEmail@example.com")
                    .fullName("userFullName")
                    .dateBirth(new Date(System.currentTimeMillis()))
                    .address("userAddress")
                    .roles(rolesDefault)
                    .build()
            );
        } else {
            userList = userGenerator.generateUsers(count, allExistingRoles);
        }
        return userList;
    }


    /*
    Генерирует, а так-же заполняет таблицы указанным количеством случайных пользователей
    Если count == null то будет записан один пользоватьель с данными по умолчанию
     */
    @Override
    public List<User> generateTestData(int count) {
        List<User> userList = generateNewUsers(count)
                .stream()
                .peek(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
                .peek(userRepository::save)
                .peek(user -> user.setPassword(PASSWORD_PLACE_HOLDER))
                .collect(Collectors.toList());
        return userList;
    }
}
