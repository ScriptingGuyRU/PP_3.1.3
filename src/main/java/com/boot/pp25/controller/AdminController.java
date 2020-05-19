package com.boot.pp25.controller;

import com.boot.pp25.model.Role;
import com.boot.pp25.model.User;
import com.boot.pp25.service.abstractServ.RoleServices;
import com.boot.pp25.service.abstractServ.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleServices roleServices;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService, RoleServices roleServices) {
        this.userService = userService;
        this.roleServices = roleServices;
    }

    @GetMapping
    public ModelAndView mainAdminControllerGet(Authentication auth){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("authUser",auth.getPrincipal());
        modelAndView.addObject("userEmail",((User) auth.getPrincipal()).getEmail());
        modelAndView.addObject("rolesAuth",((User) auth.getPrincipal()).getRoles()
                .stream().map(Objects::toString).collect(Collectors.joining(" ")));
        modelAndView.setViewName("adminsPages/admin");
        return modelAndView;
    }


    public Set<Role> getSetRoles(String role){
        Set<Role> userRoles = new HashSet<>();
        if (role.equals("user")) {
            userRoles.add(roleServices.getRoleByName("USER"));
        }
        if (role.equals("admin")) {
            userRoles.add(roleServices.getRoleByName("ADMIN"));
        }
        if (role.equals("adminAndUser")) {
            userRoles.add(roleServices.getRoleByName("ADMIN"));
            userRoles.add(roleServices.getRoleByName("USER"));
        }
        return userRoles;
    }

}
