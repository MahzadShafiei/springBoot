package com.mahzad.ipRegistery.repository;

import com.mahzad.ipRegistery.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPRepository extends JpaRepository<IP,Integer> {
}
