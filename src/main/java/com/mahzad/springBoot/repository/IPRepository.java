package com.mahzad.springBoot.repository;

import com.mahzad.springBoot.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPRepository extends JpaRepository<IP,Integer> {
}
