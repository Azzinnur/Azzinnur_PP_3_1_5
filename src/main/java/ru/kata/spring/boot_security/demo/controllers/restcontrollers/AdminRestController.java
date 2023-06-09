package ru.kata.spring.boot_security.demo.controllers.restcontrollers;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.UserIdNotFoundException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("api/admin")
public class AdminRestController {
    private final UserService userService;

    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user==null) {
            throw new UserIdNotFoundException(String.format("User with id %d is absent in the database!", id));
        }
        return user;
    }

    @PutMapping(value = "/")
    public User saveNewUser(@RequestBody User user) {
        userService.add(user);
        return user;
    }

    @PatchMapping(value = "/")
    public User saveUser(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return String.format("User with ID = %d was deleted", id);
    }

}
