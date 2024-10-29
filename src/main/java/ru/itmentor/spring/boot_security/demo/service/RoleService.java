package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;


public interface RoleService {

    // Создать новую роль
    Role createRole(Role role);

    // Найти роль по ID
    Role findRoleById(int id);

    // Найти роль по имени
    Optional<Role> findRoleByName(String name);

    // Найти все роли
    List<Role> findAllRoles();

    // Обновить роль
    Role updateRole(Role role);

    // Удалить роль по ID
    void deleteRoleById(int id);
}
