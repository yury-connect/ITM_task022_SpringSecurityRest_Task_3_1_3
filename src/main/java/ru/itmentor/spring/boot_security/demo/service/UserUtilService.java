package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;


/**
 * Сервис для генерации и тестирования пользователей
 */
public interface UserUtilService {

    List<User> generateNewUsers(int count);

    List<UserDTO> generateTestData(int count);

}
