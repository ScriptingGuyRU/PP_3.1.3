package com.boot.pp25.repository;

import com.boot.pp25.dto.UserDto;
import com.boot.pp25.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository{
    List<UserDto> getAllUsers();

    boolean addUser(User user);

    boolean delete(Long id);

    boolean editUser(User userDto);

    User getUserById(Long id);

    User getUserByName(String s);

    User getUserByEmail(String email);
}
