package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.itmentor.spring.boot_security.demo.service.UserService; // тут циклическая ссылка получится


/**
 * Конфигурация безопасности
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler successUserHandler;
    private final UserService userService; // тут циклическая ссылка получится
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler successUserHandler, UserService userService, PasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests() // определяет, какие пользователи могут получить доступ к каким URL на основе их ролей и аутентификационного статуса.
                .antMatchers("/css/**", "/public/**", "/login").permitAll() // ВСЕМ: Разрешить доступ к стилям и публичным страничкам

                .antMatchers("/authenticated/admin").permitAll() // УДАЛИТЬ

                .antMatchers("/authenticated/**").authenticated() // если пойдем в сторону "/authenticated/**" то пустит только Аутентифицированных.
                .antMatchers("/authenticated/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN") // на страницы пользователей пускаем только С РОЛЬЮ 'USER', 'ADMIN' и 'SUPERADMIN'
                .antMatchers("/authenticated/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
                .anyRequest().authenticated() // любой другой запрос требует аутентификации. Если запрос не подпадает под вышеперечисленные условия, пользователю все равно нужно будет пройти аутентификацию.

            .and()
                .formLogin()
//                .loginPage("/login")
                .successHandler(successUserHandler) // кастомный обработчик для перенаправления при успешной аутентификации
                .permitAll()

            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();

        http.csrf().disable(); // временно отключить CSRF-защиту
        return http.build();
    }


    // Настройка AuthenticationManager, используя UserService
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//        return auth.build();
//    }
}