package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Конфигурация безопасности
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler successUserHandler;
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler successUserHandler,
                          JwtRequestFilter jwtRequestFilter) {
        this.successUserHandler = successUserHandler;
        this.jwtRequestFilter = jwtRequestFilter;
    }



    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка для REST API и защищенных маршрутов
            .authorizeRequests() // для REST-эндпоинтов (/api/admin/** и /api/public/**). // Использование httpBasic() для REST. // CSRF отключен для REST API.
                .antMatchers(HttpMethod.GET, "/api/authenticated/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/api/authenticated/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers("/api/public/**", "/css/**", "/public/**",  "/loginURL", "/registrate", "/api/auth/**").permitAll() // ВСЕМ: Разрешить доступ к стилям и публичным страничкам

                .antMatchers("/authenticated/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN") // на страницы пользователей пускаем только С РОЛЬЮ 'USER', 'ADMIN' и 'SUPERADMIN'
                .antMatchers("/authenticated/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
                .antMatchers("/authenticated/superadmin/**").hasAnyRole("SUPERADMIN") // в СУПЕР админку пускаем только С РОЛЯМИ 'SUPERADMIN'
                .anyRequest().authenticated() // любой другой запрос требует аутентификации. Если запрос не подпадает под вышеперечисленные условия, пользователю все равно нужно будет пройти аутентификацию.

            .and()
                .csrf().disable() // Отключаем CSRF для REST API   // и останется включенным для защищенных маршрутов MVC (кроме тех, которые находятся в исключениях).
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

            .formLogin() // по умолчанию Spring сгенерит форму (как в нашем случае), ЛИБО для авторизации будет 'НАША красивая сверстанная форма'
                .successHandler(successUserHandler) // кастомный обработчик для перенаправления при успешной аутентификации
                .permitAll()  // Разрешение доступа к странице логина для всех

            .and()
                .logout() // настройки выхода из системы
                .logoutSuccessUrl("/") // при 'logout' будет вести на корневую страницу нашего приложения.
                .permitAll() // Разрешение доступа к странице при 'logout' для всех

            .and()
                .sessionManagement() // ограничение на количество активных сессий для одного пользователя или указать параметры сессий для повышения безопасности
                .maximumSessions(3)
                .expiredUrl("/session-expired")



//                .and()
//                .httpBasic()  // HTTP Basic для REST API

                // Настройка для MVC
                // В SecurityConfig роли должны быть указаны без префикса ROLE_ в методах hasRole и hasAnyRole. Spring Security автоматически добавляет префикс ROLE_ для ролей, поэтому при указании hasAnyRole("ADMIN", "SUPERADMIN") Spring будет проверять наличие ролей ROLE_ADMIN и ROLE_SUPERADMIN.
//            .authorizeRequests() // authorizeRequests() определяет доступ к страницам и контроллерам MVC (/authenticated/user/**, /authenticated/admin/**, и т. д.).
//              .and()
//                .formLogin(login -> login.loginPage("/loginURL")) //  для авторизации будет НАША красивая сверстанная форма
//                .loginPage("sevice-pages/login_page") // Указывает Spring Security на кастомную страницу логина                .loginPage("/loginURL")
//                .loginPage("/login") // или можно '.loginPage("/custom-login")' Указывает Spring Security на кастомную страницу логина
//                .loginProcessingUrl("/custom-login-processing") // -чтобы запросы на вход обрабатывались по определенному URL
//                .failureHandler() // кастомный обработчик для ошибки авторизации

//                .and()
//                .rememberMe() // Функция "Запомнить меня" полезна, если вы хотите, чтобы пользователи оставались залогиненными после закрытия браузера.
//                .key("uniqueAndSecretKey")  // Уникальный ключ для функции
//                .tokenValiditySeconds(86400)  // Срок действия в секундах (например, один день)
//                .userDetailsService(userDetailsService()) // определяем UserDetailsService

//                .and()
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/css/**", "/public/**") // Исключить определенные URL из CSRF-защиты
        ;
//        http.csrf().disable(); // ЛИБО можно так - временно отключить CSRF-защиту
        return http.build();
    }
}