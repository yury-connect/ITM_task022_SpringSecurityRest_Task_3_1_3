package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {

    // Создать нового пользователя
    User createUser(User user);

    // Найти пользователя по ID
    User findUserById(int id);

    // Найти пользователя по userName
    User findUserByUsername(String userName);

    // Найти всех пользователей
    List<User> findAllUsers();

    // Обновить информацию о пользователе
    void updateUser(User user);

    // Удалить пользователя по ID
    void deleteUserById(int id);
}
