package com.mahzad.springBoot.service;

import com.mahzad.springBoot.dto.CreateUserRequest;
import com.mahzad.springBoot.model.Role;
import com.mahzad.springBoot.model.User;
import com.mahzad.springBoot.model.UserRole;
import com.mahzad.springBoot.repository.RoleRepository;
import com.mahzad.springBoot.repository.UserRepository;
import com.mahzad.springBoot.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setAge(createUserRequest.getAge());

        for(Integer roleId : createUserRequest.getRoleIds())
        {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            UserRole  userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            user.getUserRoles().add(userRole);
        }

        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
