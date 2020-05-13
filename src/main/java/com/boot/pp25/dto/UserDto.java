package com.boot.pp25.dto;

import com.boot.pp25.model.Role;
import com.boot.pp25.model.User;

import java.util.Arrays;
import java.util.Objects;

public class UserDto {

    private Long id;
    private String UserName;
    private String LastName;
    private String password;
    private String email;
    private int age;
    private String[] roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.UserName = user.getUserName();
        this.LastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.age = user.getAge();
        Object[] objectArr = user.getRoles().stream().map(Role::getName).toArray();
        this.roles = Arrays.copyOf(objectArr, objectArr.length, String[].class);
    }

    public UserDto(Long id, String userName, String lastName, String password, String email, int age, String[] roles) {
        this.id = id;
        this.UserName = userName;
        this.LastName = lastName;
        this.password = password;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return age == userDto.age &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(UserName, userDto.UserName) &&
                Objects.equals(LastName, userDto.LastName) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(email, userDto.email) &&
                Arrays.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, UserName, LastName, password, email, age);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }
}
