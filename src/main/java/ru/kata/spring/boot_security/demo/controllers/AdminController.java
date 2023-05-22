package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/admin")
    public String getAllUsers(Principal principal, Model model) {
        List<User> userList = userService.getAllUsers();
        User admin = userService.findUserByUsername(principal.getName());
        model.addAttribute("users", userList);
        model.addAttribute("admin", admin);
        return "admin";
    }

    @GetMapping(value = "/admin/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "new_user";
    }

    @PutMapping(value = "/admin/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("allRoles", roles);
            return "new_user";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/admin/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("allRoles", roles);
            return "edit_user";
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/editUser/{id}")
    public String editUser(@PathVariable long id, Model model) {
        User user = userService.findUserById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "edit_user";
    }

    @DeleteMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
