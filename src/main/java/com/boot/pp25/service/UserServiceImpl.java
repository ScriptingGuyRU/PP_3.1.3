package com.boot.pp25.service;

import com.boot.pp25.dto.UserDto;
import com.boot.pp25.model.User;
import com.boot.pp25.repository.UserRepository;
import com.boot.pp25.service.abstractServ.RoleServices;
import com.boot.pp25.service.abstractServ.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleServices roleServices;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServices roleServices) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleServices = roleServices;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDto userDto = new UserDto(user);
        return userDto;
    }

    @Override
    public void saveUser(UserDto userDto) {
       User user = new User(userDto);
       user.setRoles(Arrays.stream(userDto.getRoles()).map(roleServices::getRoleByName).collect(Collectors.toSet()));
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.addUser(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public UserDto findUserByUserName(String s) {
        return new UserDto(userRepository.getUserByName(s));

    }

    @Override
    public boolean editUser(UserDto userDto) {
        User user = new User(userDto);
        user.setRoles(Arrays.stream(userDto.getRoles()).map(roleServices::getRoleByName).collect(Collectors.toSet()));
        return userRepository.editUser(user);
    }
}
