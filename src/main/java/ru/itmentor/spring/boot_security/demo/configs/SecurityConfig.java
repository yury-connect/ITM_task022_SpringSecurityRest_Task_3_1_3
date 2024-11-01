package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .antMatchers("/css/**", "/public/**", "/login", "/loginURL", "/registrate").permitAll() // ВСЕМ: Разрешить доступ к стилям и публичным страничкам

                .antMatchers("/authenticated/**").authenticated() // если пойдем в сторону "/authenticated/**" то пустит только Аутентифицированных.
                .antMatchers("/authenticated/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN") // на страницы пользователей пускаем только С РОЛЬЮ 'USER', 'ADMIN' и 'SUPERADMIN'
                .antMatchers("/authenticated/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
                .anyRequest().authenticated() // любой другой запрос требует аутентификации. Если запрос не подпадает под вышеперечисленные условия, пользователю все равно нужно будет пройти аутентификацию.

            .and()
                .formLogin() // по умолчанию Spring сгенерит форму (как в нашем случае), ЛИБО для авторизации будет 'НАША красивая сверстанная форма'
//                .formLogin(login -> login.loginPage("/loginURL")) //  для авторизации будет НАША красивая сверстанная форма


//                .loginPage("sevice-pages/login_page") // Указывает Spring Security на кастомную страницу логина
                .loginPage("/loginURL")

//                .loginPage("/login") // или можно '.loginPage("/custom-login")' Указывает Spring Security на кастомную страницу логина
//                .loginProcessingUrl("/custom-login-processing") // -чтобы запросы на вход обрабатывались по определенному URL
//                .failureHandler() // кастомный обработчик для ошибки авторизации
                .successHandler(successUserHandler) // кастомный обработчик для перенаправления при успешной аутентификации
                .permitAll()  // Разрешение доступа к странице логина для всех

            .and()
                .logout() // настройки выхода из системы
                .logoutSuccessUrl("/") // при 'logout' будет вести на корневую страницу нашего приложения.
                .permitAll() // Разрешение доступа к странице при 'logout' для всех

//                .and()
//                .rememberMe() // Функция "Запомнить меня" полезна, если вы хотите, чтобы пользователи оставались залогиненными после закрытия браузера.
//                .key("uniqueAndSecretKey")  // Уникальный ключ для функции
//                .tokenValiditySeconds(86400)  // Срок действия в секундах (например, один день)
//                .userDetailsService(userDetailsService()) // определяем UserDetailsService

                .and()
                .sessionManagement() // ограничение на количество активных сессий для одного пользователя или указать параметры сессий для повышения безопасности
                .maximumSessions(3)
                .expiredUrl("/session-expired")

//                .and()
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/css/**", "/public/**") // Исключить определенные URL из CSRF-защиты
        ;
        http.csrf().disable(); // ЛИБО можно так - временно отключить CSRF-защиту
        return http.build();


    /*
        // аутентификация -= InMemory =-
        @Bean
        @Override
        public UserDetailsService userDetailsService() {
            UserDetails user =
                    User.withDefaultPasswordEncoder()
                            .username("user")
                            .password("user")
                            .roles("USER")
                            .build();
            return new InMemoryUserDetailsManager(user);
        }
    */
    }
}