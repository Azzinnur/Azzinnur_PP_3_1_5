package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100, message = "Поле должно быть не менее 3 и не более 100 символов длиной!")
    @Column(name = "firstname")
    private String firstname;

    @Size(min = 3, max = 100, message = "Поле должно быть не менее 3 и не более 100 символов длиной!")
    @Column(name = "lastname")
    private String lastname;

    @Min(value=7, message = "Зарегистрировать пользователя можно только с 7 лет!")
    @Max(value=200, message = "Введите валидный возраст!")
    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Введите валидный E-mail!")
    @Email(message = "Введите валидный E-mail!")
    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection<Role> roles;

    public User() {
    }

    public User(String username, String password, String email) {
        this.firstname = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Collection<Role> roles){
        this.roles = roles;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
