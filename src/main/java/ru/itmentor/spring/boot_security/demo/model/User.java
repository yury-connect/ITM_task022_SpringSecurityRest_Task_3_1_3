package ru.itmentor.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false) // поле не может быть обновлено при выполнении операции обновления (UPDATE) в БД...
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "user_date_birth")
    private Date dateBirth;

    @Column(name = "user_address")
    private String address;


    @ManyToMany(fetch = FetchType.EAGER)                        // FetchType.EAGER удобно, но может привести к проблемам производительности, если кол-во ролей велико или ты часто работаешь с большим количеством пользователей.
    @JoinTable(                                                 // Это обязательный элемент для @ManyToMany, так как необходимо указать, как данные должны быть связаны на уровне базы данных
            name = "user_roles",                                // название таблицы в базе данных, которая будет хранить связи между User и Role. В данном случае, таблица называется user_roles
            joinColumns = @JoinColumn(name = "user_id"),        // указ. на кол. в табл. user_roles, кот-я ссылается на user_id из таблицы users. Эта кол. будет содержать id пользователей. // name = "user_id": это назв. кол. в табл. user_roles, кот. будет хранить id пользователей.
            inverseJoinColumns = @JoinColumn(name = "role_id")  // указ. на кол. в табл. user_roles, кот-я ссылается на role_id из таблицы roles. Эта кол. будет содержать id ролей // name = "role_id": это назв. кол. в табл. user_roles, кот. будет хранить id ролей.
    )
    private Set<Role> roles;
}

/*
User связан с Role через аннотацию @ManyToMany и соответствующую таблицу связей (user_roles)
 */