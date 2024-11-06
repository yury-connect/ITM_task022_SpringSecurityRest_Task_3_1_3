package ru.itmentor.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;


@Data
@Getter
@Builder
@AllArgsConstructor
public class UserDTO {

    private int id;

//    @NotNull
    private String userName;

//    @NotNull
    private String password;

    private String email;

    private String fullName;

    private Date dateBirth;

    private String address;

    private List<String> roles; // Список названий ролей
}
