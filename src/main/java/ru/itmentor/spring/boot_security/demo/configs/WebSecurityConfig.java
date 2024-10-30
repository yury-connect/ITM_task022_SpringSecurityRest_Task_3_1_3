package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;


    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // определяет, какие пользователи могут получить доступ к каким URL на основе их ролей и аутентификационного статуса.
//                .antMatchers("/", "/index").permitAll()
                .antMatchers("/css/**", "/public/**").permitAll() // ВСЕМ: Разрешить доступ к стилям и публичным страничкам

                .antMatchers("/authenticated/**").authenticated() // если пойдем в сторону "/authenticated/**" то пустит только Аутентифицированных.
                .antMatchers("/authenticated/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN") // на страницы пользователей пускаем только С РОЛЬЮ 'USER', 'ADMIN' и 'SUPERADMIN'
                .antMatchers("/authenticated/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
                .anyRequest().authenticated() // любой другой запрос требует аутентификации. Если запрос не подпадает под вышеперечисленные условия, пользователю все равно нужно будет пройти аутентификацию.

                .and()
//                .httpBasic() // стандартная Аутентификация
                .formLogin() // для авторизации будет 'НАША красивая сверстанная форма'/ либо по умолчанию Spring сгенерит, как в нашем случае.
//                .loginPage("/custom-login")  // Указывает Spring Security на кастомную страницу логина
//                .loginProcessingUrl("/custom-login-processing") // -чтобы запросы на вход обрабатывались по определенному URL
//                .failureHandler() // кастомный обработчик для ошибки авторизации
                .permitAll()  // Разрешение доступа к странице логина для всех
                .successHandler(successUserHandler) // кастомный обработчик для перенаправления при успешной аутентификации

                .and()
                .logout() // настройки выхода из системы
                .logoutSuccessUrl("/") // при 'logout' будет вести на корневую страницу нашего приложения.
                .permitAll()  // Разрешение доступа к странице при 'logout' для всех

//                .and()
//                .rememberMe() // Функция "Запомнить меня" полезна, если вы хотите, чтобы пользователи оставались залогиненными после закрытия браузера.
//                .key("uniqueAndSecretKey")  // Уникальный ключ для функции
//                .tokenValiditySeconds(86400)  // Срок действия в секундах (например, один день)
//                .userDetailsService(userDetailsService()) // определяем UserDetailsService

                .and()
                .sessionManagement() // ограничение на количество активных сессий для одного пользователя или указать параметры сессий для повышения безопасности
                .maximumSessions(1)
                .expiredUrl("/session-expired")

//                .and()
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/css/**", "/public/**") // Исключить определенные URL из CSRF-защиты
        ;

        http.csrf().disable(); // временно отключить CSRF-защиту
    }

    // аутентификация inMemory
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
}