package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {

    void save(User user);

    User getById(int id);

    List<User> getAll();

    void update(User user);

    void delete(int id);
}
