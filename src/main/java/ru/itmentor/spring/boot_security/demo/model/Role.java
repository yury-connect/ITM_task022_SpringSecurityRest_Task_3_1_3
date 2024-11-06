package ru.itmentor.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false) // поле не может быть обновлено при выполнении операции обновления (UPDATE) в БД...
    private int id;

    @NotNull
    @Column(name = "role_name",nullable = false, unique = true)
    private String name;


    @Override
    public String toString() {
        return name; // Важно, чтобы toString возвращало именно ИМЯ роли, использую при парсинге родей из org.springframework.security.core.authority.AuthorityUtils
    }
}