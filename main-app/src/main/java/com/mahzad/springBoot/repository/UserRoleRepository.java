package com.mahzad.springBoot.repository;

import com.mahzad.springBoot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
}
