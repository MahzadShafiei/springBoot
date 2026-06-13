package com.mahzad.springBoot.dto;

import java.util.List;

public class CreateUserRequest {
    private String name;
    private Integer age;
    private List<Integer> roleIds;

    //getter setter
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

    public List<Integer> getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
