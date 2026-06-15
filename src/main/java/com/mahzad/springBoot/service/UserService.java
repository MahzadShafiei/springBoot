package com.mahzad.springBoot.service;

import com.mahzad.springBoot.dto.CreateUserRequest;
import com.mahzad.springBoot.dto.UserResponse;
import com.mahzad.springBoot.model.Role;
import com.mahzad.springBoot.model.User;
import com.mahzad.springBoot.model.UserRole;
import com.mahzad.springBoot.repository.RoleRepository;
import com.mahzad.springBoot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponse> createUser(CreateUserRequest createUserRequest) {
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
        userRepository.save(user);
        return getAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<UserResponse> getAll() {
        var users =userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for( User user : users )
        {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setAge(user.getAge());
            userResponse.setRoles(
                    user.getUserRoles()
                            .stream()
                            .map(c-> c.getRole().getTitle())
                            .toList()
            );
            userResponseList.add(userResponse);

        }
        return userResponseList;

    }
}
