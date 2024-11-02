package ru.itmentor.spring.boot_security.demo.configs;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/authenticated/admin/**") // Это позволит видеть документацию только для REST-эндпоинтов, начинающихся с /authenticated/admin.
//                .build();
//    }
}
