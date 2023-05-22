package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/user")
    public String getUser(Principal principal, Model model){
        User user = userServiceImpl.findUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}

