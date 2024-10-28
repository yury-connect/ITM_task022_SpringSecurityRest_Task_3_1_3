package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.dao.UserDao;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;


@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    private UserDao dao;


    @Autowired
    public UserServiceImpl(@Qualifier("daoImpDB") UserDao dao) { // daoImplList   or   daoImpDB
        this.dao = dao;
    }



    @Override
    @Transactional // Теперь транзакции контролируются на уровне сервиса
    public void save(User user) {
        dao.save(user);
    }


    @Override
    @Transactional(readOnly = true) // Транзакция для операций чтения // не изменяют данные
    public User getById(int id) {
        return dao.getById(id);
    }


    @Override
    @Transactional(readOnly = true) // Транзакция для операций чтения // не изменяют данные
    public List<User> getAll() {
        return dao.getAll()  ;
    }


    @Override
    @Transactional // Обновление также проходит в рамках транзакции
    public void update(User user) {
        dao.update(user);
    }


    @Override
    @Transactional // Удаление в транзакции
    public void delete(int id) {
        dao.delete(id);
    }
}
