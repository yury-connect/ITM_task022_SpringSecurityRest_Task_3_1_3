package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;
import ru.itmentor.spring.boot_security.demo.util.UserGenerator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


@Service
public class UserUtilServiceImpl implements UserUtilService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGenerator userGenerator;
    private final DtoUtils dtoUtils;


    @Autowired
    public UserUtilServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder,
                               UserGenerator userGenerator,
                               DtoUtils dtoUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGenerator = userGenerator;
        this.dtoUtils = dtoUtils;
    }



    /**
     * Генерация случайных пользователей;
     * Если count < 1, то создается один пользователь с значениями по умолчанию;
     * Если count >= 1, то генерируется указанное количество пользователей с фейковыми данными;
     * @param count - необходимое/ требуемое количество пользователей;
     * @return - возвращает список тестовых пользователей с фейковыми данными;
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


    /**
     * Генерирует, а так-же заполняет таблицы указанным количеством случайных пользователей;
     * Если count == null то будет записан один пользоватьель с данными по умолчанию;
     * @param count - На вход принимает необходимое количество генерируемых тестовых пользователей;
     * @return - На выходе -возвращает уже помещенных в БД (т.е. уже существующих) и найденных в ней пользователей;
     */
    @Override
    public List<UserDTO> generateTestData(int count) {
        // Генерируем случайных пользователей
        List<User> usersList = generateNewUsers(count).stream()
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userRepository.save(user);
                })
                .collect(Collectors.toList());

        // Необходимо найти сохраненных в БД пользователей, преобразовать их в List<UserDTO> и вернуть его.
        List<UserDTO> usersDtoList = usersList.stream()
                .map(usr -> userRepository.findByUserName(usr.getUserName())
                        .map(dtoUtils::convertToUserDto)
                        .orElseThrow(
                                () -> new RuntimeException("User not found: " + usr.getUserName())
                        )
                )
                .collect(Collectors.toList());
        return usersDtoList;
    }
}
