package ru.itmentor.spring.boot_security.demo.util;

import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public  class UserUtils {

    private static final String USER_PASSWORD_DEFAULT = "1";


    public static List<User> generateUsers(int count) {
        List<User> users = Stream.generate(() -> generateUser())
                .limit(count) // Количество элементов в списке
                .collect(Collectors.toList());
        return users;
    }


    private static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }


    public static User generateUser() {
        Faker faker = new Faker(new Locale("ru"));
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");   // Создание транслитератора

        String fullName = faker.name().fullName(); // Генерируем полное имя пользователя

        String[] partsUserName = fullName.split(" "); // Разделяем полное имя на части (по пробелам)
        String firstUserName = partsUserName[1].toLowerCase(Locale.ENGLISH); // Имя
        String lastUserName = partsUserName[0].toLowerCase(Locale.ENGLISH);  // Фамилия
        String userName = transliterator.transliterate(firstUserName + "." + lastUserName); // Генерируем userName на основе полного имени

        String email = faker.internet().emailAddress(userName);

        String password = USER_PASSWORD_DEFAULT;

        final LocalDate startDate = LocalDate.of(1970, 1, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 1);
        Date dateBirth = Date.valueOf(generateRandomDate(startDate, endDate));
        String address = faker.address().fullAddress();

        return User.builder()
                .id(-1)
                .userName(userName)
                .password(password)
                .email(email)
                .fullName(fullName)
                .address(address)
                .dateBirth(dateBirth)
                .build();
    }
}
