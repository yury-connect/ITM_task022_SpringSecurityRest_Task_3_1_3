package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.constants.Constants;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allExistingUsers = userRepository.findAll();
        allExistingUsers.sort(Comparator.comparing(User::getId)); // сортируем отдаваемый список по возрастанию id
        return allExistingUsers;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (Constants.PASSWORD_PLACE_HOLDER.equals(user.getPassword())) {
            // Если пароль не поменяется, когда обновленный пользователь прилетел из формы - то восстанавливаем прежний пароль;
            final String previousPassword = userRepository.findById(user.getId()).get().getPassword();
            user.setPassword(previousPassword);  // Восстанавливаем прежний пароль при сохранении в БД.;
        } else {
            // Если пароль поменяется (т.е. введен новый), то кодируем новый пароль;
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet())
        );
    }

//    private Collection<GrantedAuthority> roleStringToGrantedAuthorities(Set<Role> roleSet) {
//        Collection<GrantedAuthority> authorities =
//                roleSet.stream()
//                        .map(str -> new SimpleGrantedAuthority(str.toString()))
//                        .collect(Collectors.toList());
//        return authorities;
//    }
}
