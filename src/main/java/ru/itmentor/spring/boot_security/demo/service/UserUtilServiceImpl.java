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
import java.util.stream.Collectors;


@Service
public class UserUtilServiceImpl implements UserUtilService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "1"; // такой пароль ПО УМОЛЧАНИЮ будет у всех пользователей



    @Autowired
    public UserUtilServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (count < 1) {
            userList = new ArrayList<>();
            userList.add(User.builder()
                    .id(-1) // при создании ставим заведомо несуществующий id
                    .userName("userLogin")
                    .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .email("userEmail")
                    .fullName("userFullName")
                    .dateBirth(new Date(System.currentTimeMillis()))
                    .address("userAddress")
                    .roles(allExistingRoles.stream().collect(Collectors.toSet()))
                    .build()
            );
        } else {
            userList = UserGenerator.generateUsers(count, allExistingRoles, passwordEncoder);
        }
        return userList;
    }


    /*
    Генерирует, а так-же заполняет таблицы указанным количеством случайных пользователей
    Если count == null то будет записан один пользоватьель с данными по умолчанию
     */
    @Override
    public void generateTestData(int count) {
        userRepository.saveAll(generateNewUsers(count));
    }
}
