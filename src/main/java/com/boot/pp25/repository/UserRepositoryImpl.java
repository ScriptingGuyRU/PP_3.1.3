package com.boot.pp25.repository;

import com.boot.pp25.dto.UserDto;
import com.boot.pp25.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
        try{
            entityManager.persist(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
                String hql = "delete from User where id = :id";
                entityManager.createQuery(hql)
                        .setParameter("id",id).executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editUser(User user) {
        try {
            entityManager.merge(user);
            return true;
        } catch (Exception e) {
                return false;
        }
    }

    @Override
    public User getUserById(Long id) {
        return  entityManager.find(User.class, id);
    }


    @Override
    public User getUserByName(String name) {
        return entityManager
                .createQuery("select u from User u where name = :name", User.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager
                .createQuery("from User where email = :email", User.class)
                .setParameter("email", email).getSingleResult();
    }
}
