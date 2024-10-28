package ru.itmentor.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;


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
}
