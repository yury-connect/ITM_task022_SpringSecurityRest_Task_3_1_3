package ru.itmentor.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
//@Table(name = "roles", catalog = "your_database", schema = "", indexes = {@Index(name = "idx_roles_role_name", columnList = "role_name")}) // можно явно указать создание таблиц с типом InnoDB
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false) // поле не может быть обновлено при выполнении операции обновления (UPDATE) в БД...
    private int id;

    @Column(name = "role_name",nullable = false, unique = true)
    private String name;


    @Override
    public String toString() {
        return name; // Важно, чтобы toString возвращало именно ИМЯ роли, использую при парсинге родей из org.springframework.security.core.authority.AuthorityUtils
    }
}