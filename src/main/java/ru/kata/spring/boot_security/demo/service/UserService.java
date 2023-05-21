package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRoles());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addOrUpdate(User user){
        User oldUser = userRepository.getById(user.getId());
        if(user.getPassword().isBlank()) {
            user.setPassword(oldUser.getPassword());
        } else {
        user.setPassword(passwordEncoder.encode(user.getPassword()));}
        userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.getById(id);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
