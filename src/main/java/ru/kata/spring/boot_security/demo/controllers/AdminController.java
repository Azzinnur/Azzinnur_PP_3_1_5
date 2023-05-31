package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Principal principal, Model model) {
        List<User> userList = userService.getAllUsers();
        User admin = userService.findUserByUsername(principal.getName());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("users", userList);
        model.addAttribute("admin", admin);
        return "admin";
    }

    @GetMapping("/admin/findUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping(value = "/admin/saveNewUser")
    public String saveNewUser(User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/admin/saveUser/{id}")
    public String saveUser(User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(User user) {
        userService.delete(user.getId());
        return "redirect:/admin";
    }
}
