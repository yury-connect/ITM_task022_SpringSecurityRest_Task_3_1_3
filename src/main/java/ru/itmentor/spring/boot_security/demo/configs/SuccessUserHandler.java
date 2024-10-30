package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


/**
 * Выполняет перенаправление в зависимости от роли пользователя
 */
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_USER")) {
//            response.sendRedirect("/user");
//        } else {
//            response.sendRedirect("/");
//        }

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        System.out.println("\n\n\tRoles: " + roles.size());
        roles.stream().forEach(System.out::println); // debug stream
        System.out.println("\n\n");

        if (roles.contains("ROLE_SUPERADMIN")) {
            System.out.println("\n\nВ систему вошел Супер-пупер Admin с ролью ROLE_SUPERADMIN\n\n");
            response.sendRedirect("/authenticated/admin/all");
        } else if (roles.contains("ROLE_ADMIN")) {
            System.out.println("\n\nВ систему вошел Admin с ролью ROLE_ADMIN\n\n");
            response.sendRedirect("/authenticated/admin/all");
        } else if (roles.contains("ROLE_USER")) {
            System.out.println("\n\nЭто просто User // ROLE_USER\n\n");
            response.sendRedirect("/authenticated/user");
        } else if (roles.contains("ROLE_GUEST")) {
            System.out.println("\n\nГость залетный Guest // ROLE_GUEST\n\n");
            response.sendRedirect("/authenticated");
        } else {
            System.out.println("\n\nРоль отсутствует //  не обработана\n\n");
            response.sendRedirect("/");
        }
    }
}