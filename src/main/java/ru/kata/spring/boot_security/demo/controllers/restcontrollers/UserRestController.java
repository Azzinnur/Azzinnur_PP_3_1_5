package ru.kata.spring.boot_security.demo.controllers.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public User getUser(Principal principal){
        return userService.findUserByUsername(principal.getName());
    }
}
