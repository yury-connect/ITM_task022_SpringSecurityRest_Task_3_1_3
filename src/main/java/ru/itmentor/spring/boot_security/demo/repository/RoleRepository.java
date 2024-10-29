package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.Query;
import ru.itmentor.spring.boot_security.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Найти роль по имени
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name LIKE %?1%")
    List<Role> searchRolesByPartialName(String partialName);
}
