package com.boot.pp25.service.abstractServ;

import com.boot.pp25.dto.UserDto;
import com.boot.pp25.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto getUserById(Long id);

    void saveUser(UserDto userDto);

    boolean deleteById(Long id);

    UserDto findUserByUserName(String s);

    boolean editUser(UserDto userDto);
}
