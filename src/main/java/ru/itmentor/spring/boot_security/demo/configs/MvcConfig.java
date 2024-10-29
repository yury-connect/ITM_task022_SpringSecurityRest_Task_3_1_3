package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Этот класс используется для настройки статичных маршрутов/ страниц
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {           // MvcConfig связывает URL '/user' со страницей 'user.html'
        registry.addViewController("/user").setViewName("user");
    }
}
