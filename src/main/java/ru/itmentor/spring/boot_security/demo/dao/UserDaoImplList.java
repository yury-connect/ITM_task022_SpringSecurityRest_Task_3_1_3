package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;


@Repository("daoImplList")
@Primary // Этот бин будет использоваться по умолчанию, если не указано иное
public class UserDaoImplList implements UserDao {

    private List<User> users;
    private int counterId;


    public UserDaoImplList() {
        this.users = new ArrayList<>();
        this.counterId = 1; // Начинаем с 1, чтобы IDs были последовательны
    }


    @Override
    public void save(User user) {
        user.setId(counterId++);
        users.add(user);
    }

    @Override
    public User getById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null); // Если не найдено, возвращаем null
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users); // Возвращаем копию списка для безопасности
    }

    @Override
    public void update(User user) {
        User existingUser = getById(user.getId()); // Находим существующего пользователя
        if (existingUser != null) {
            // Обновляем поля, если пользователь найден
            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            existingUser.setFullName(user.getFullName());
            existingUser.setDateBirth(user.getDateBirth());
            existingUser.setAddress(user.getAddress());
        }
    }

    @Override
    public void delete(int id) {
        users.removeIf(user -> user.getId() == id); // Удаляем пользователя по ID
    }
}
