package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100, message = "First name length should be not less than 3 and not more than 100 symbols!")
    @Column(name = "firstname")
    private String firstname;

    @Size(min = 3, max = 100, message = "Last name length should be not less than 3 and not more than 100 symbols!")
    @Column(name = "lastname")
    private String lastname;

    @Min(value=7, message = "User should be elder the 7 years old to register!")
    @Max(value=200, message = "Please enter valid age!")
    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Please enter valid E-mail!")
    @Email(message = "Please enter valid E-mail!")
    @Column(name = "email")
    private String email;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Role> roles;

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

    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public Set<Role> getAuthorities() {
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
