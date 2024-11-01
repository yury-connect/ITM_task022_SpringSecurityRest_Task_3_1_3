package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;



    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public Optional<Role> findRoleByName(String name) { // !!!
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAllRoles() { // !!!
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Role role) {
        if (!roleRepository.existsById(role.getId())) {
            throw new RuntimeException("Role not found with id: " + role.getId());
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(int id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
}