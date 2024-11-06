package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;

@Component
public class DtoUtils {

    private final RoleRepository roleRepository;

    @Autowired
    public DtoUtils(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDTO convertToUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(PASSWORD_PLACE_HOLDER)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .dateBirth(user.getDateBirth())
                .address(user.getAddress())
                .roles(convertRolesToStrings(user.getRoles())) // Используем метод для преобразования ролей
                .build();
    }

    public User convertToUser(UserDTO dto) {
        User user = User.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .dateBirth(dto.getDateBirth())
                .address(dto.getAddress())
                .build();

        user.setRoles(convertStringsToRoles(dto.getRoles())); // Используем метод для преобразования имен ролей
        return user;
    }

    // Метод для преобразования Set<Role> в List<String> имен ролей
    private List<String> convertRolesToStrings(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    // Метод для преобразования List<String> в Set<Role>
    private Set<Role> convertStringsToRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(this::findRoleByName) // Используем ссылку на метод, который обрабатывает Optional
                .collect(Collectors.toSet());
    }

    // Метод для поиска роли по имени и обработки Optional
    private Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role name: " + roleName));
    }
}
