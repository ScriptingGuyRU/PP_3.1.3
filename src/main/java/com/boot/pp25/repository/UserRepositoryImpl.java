package com.boot.pp25.repository;

import com.boot.pp25.dto.UserDto;
import com.boot.pp25.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<UserDto> getAllUsers() {
        List<User> userList = entityManager.createQuery("From User").getResultList();
        List<UserDto> userDtoList = userList
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public boolean addUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.save(user);

        return true;
    }

    @Override
    public boolean delete(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editUser(User user) {
        if (entityManager.find(User.class, user.getId()) != null) {
            entityManager.merge(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserById(Long id) {
        User user = entityManager.find(User.class, id);

        return user;
    }


    @Override
    public User getUserByName(String name) {
        User user = (User) entityManager
                .createQuery("select u from User u where name = :name")
                .setParameter("name", name).getSingleResult();
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = (User) entityManager
                .createQuery("from User where email = :email", User.class)
                .setParameter("email", email).getSingleResult();
        return user;
    }
}
