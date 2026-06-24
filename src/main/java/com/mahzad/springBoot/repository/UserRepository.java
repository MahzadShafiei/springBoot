package com.mahzad.springBoot.repository;

import com.mahzad.springBoot.dto.UserResponse;
import com.mahzad.springBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

        @Query("""
           SELECT DISTINCT u
           FROM User u
           LEFT JOIN FETCH u.userRoles ur
           LEFT JOIN FETCH ur.role
           """)
        List<User> getAllByJpql();

        @Query("""
                SELECT DISTINCT u
                FROM User u
                LEFT JOIN u.userRoles ur
                LEFT JOIN ur.role
                WHERE u.id = :id
                """)
        Optional<User> getUserByIdJpql(Integer id);

        @Query("""
                SELECT u
                FROM User u
                LEFT JOIN FETCH u.userRoles ur
                LEFT JOIN FETCH ur.role r
                WHERE r.id = :id
                """)
        List<User> getUsersByRoleJpql(Integer id);

}
