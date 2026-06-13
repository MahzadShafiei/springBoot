package com.mahzad.springBoot.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles = new ArrayList<>();

    //constructor
    public Role() {}

    //getter setter
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


}
