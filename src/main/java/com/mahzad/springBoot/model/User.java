package com.mahzad.springBoot.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    //constructor
    public User() {}

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    //getter setter
    public int  getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
