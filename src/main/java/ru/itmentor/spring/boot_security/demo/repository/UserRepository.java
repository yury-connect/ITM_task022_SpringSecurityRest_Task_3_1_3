package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.Query;
import ru.itmentor.spring.boot_security.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {  // 1й параметр User указывает на сущность, с которой работает репозиторий, 2й параметр Integer — это тип данных для поля id

    // Поиск пользователя по имени
    Optional<User> findByUserName(String userName);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

}

    /*
    интересный вариант с ленивой инициализацией сущностей, ниже ссылка на источник:
    https://github.com/AlexKudryashov1/Task-7/blob/Task-3.1.3x/src/main/java/ru/itmentor/spring/boot_security/demo/repositories/UserRepositories.java

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    User findByUsernameAndFetchLazyRelationEagerly(@Param("username") String username);
     */