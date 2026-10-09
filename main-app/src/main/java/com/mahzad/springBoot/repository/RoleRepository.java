package com.mahzad.springBoot.repository;

import com.mahzad.springBoot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
