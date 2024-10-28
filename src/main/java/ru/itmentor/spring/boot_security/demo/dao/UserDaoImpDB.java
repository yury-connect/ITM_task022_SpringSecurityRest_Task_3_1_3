package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository("daoImpDB")
public class UserDaoImpDB implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;



   @Override
   public void save(User user) {
      if (user.getId() == 0) {
         entityManager.persist(user); // Для новых объектов используем persist()
      } else {
         entityManager.merge(user); // Для существующих объектов используем merge()
      }
   }


   @Override
   public User getById(int id) {
      return entityManager.find(User.class, id);
   }


   @Override
   @SuppressWarnings("unchecked")
   public List<User> getAll() {
      TypedQuery<User> query = entityManager.createQuery("FROM User ORDER BY id ASC", User.class);
      return query.getResultList();
   }


   @Override
   public void update(User user) {
      entityManager.merge(user); // merge обновляет существующий объект в базе данных
   }


   @Override
   public void delete(int id) {
      User user = entityManager.find(User.class, id);
      if (user != null) {
         entityManager.remove(user);
      }
   }
}
