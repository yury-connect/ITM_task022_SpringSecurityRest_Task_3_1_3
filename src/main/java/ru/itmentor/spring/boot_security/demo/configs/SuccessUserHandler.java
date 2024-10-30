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
        if (roles.contains("ROLE_SUPERADMIN") || roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/authenticated/admin");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/authenticated/user");
        } else if (roles.contains("ROLE_GUEST")) {
            response.sendRedirect("/authenticated");
        } else {
            response.sendRedirect("/");
        }
    }
}