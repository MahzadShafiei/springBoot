package com.mahzad.springBoot.repository;

import com.mahzad.springBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
