package ru.itmentor.spring.boot_security.demo.util;

import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.USER_PASSWORD_DEFAULT;


/**
 * @author Yury
 * Утилитный класс для генерации новых фейковых тестовых пользователей
 */
@Component
public final class UserGenerator {

    private PasswordEncoder passwordEncoder;
    private static final Random RANDOM = new Random();


    @Autowired
    public UserGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    public List<User> generateUsers(int count, List<Role> allExistingRoles) {
        List<User> users = Stream.generate(() -> generateUser(allExistingRoles))
                .limit(count) // Количество элементов в списке
                .collect(Collectors.toList());
        return users;
    }


    private LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }


    public User generateUser(List<Role> allExistingRoles) {
        Faker faker = new Faker(new Locale("ru"));
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");   // Создание транслитератора

        String fullName = faker.name().fullName(); // Генерируем полное имя пользователя

        String[] partsUserName = fullName.split(" "); // Разделяем полное имя на части (по пробелам)
        String firstUserName = partsUserName[1].toLowerCase(Locale.ENGLISH); // Имя
        String lastUserName = partsUserName[0].toLowerCase(Locale.ENGLISH);  // Фамилия
        String userName = transliterator.transliterate(firstUserName + "." + lastUserName); // Генерируем userName на основе полного имени

        String email = faker.internet().emailAddress(userName);

        String password = USER_PASSWORD_DEFAULT;
//        String password = passwordEncoder.encode(USER_PASSWORD_DEFAULT);

        final LocalDate startDate = LocalDate.of(1970, 1, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 1);
        Date dateBirth = Date.valueOf(generateRandomDate(startDate, endDate));
        String address = faker.address().fullAddress();

        // Перемешиваем список ролей для случайного порядка
        List<Role> shuffledRoles = new ArrayList<>(allExistingRoles);
        Collections.shuffle(shuffledRoles);

        // Получаем случайное количество ролей
        int rolesCount = RANDOM.nextInt(allExistingRoles.size()) + 1;

        // Собираем уникальные роли в Set, взяв первые rolesCount элементов из перемешанного списка
        Set<Role> roles = shuffledRoles.stream()
                .limit(rolesCount)
                .collect(Collectors.toSet());

        return User.builder()
                .id(-1)
                .userName(userName)
                .password(password)
                .email(email)
                .fullName(fullName)
                .dateBirth(dateBirth)
                .address(address)
                .roles(roles)
                .build();
    }
}
