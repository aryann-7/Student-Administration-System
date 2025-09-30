package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.models.Role;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //everything run in 1 transaction
    @Override
    @Transactional

    public void run(String... args) throws Exception {
        //make or get roles
        Role adminRole = createOrRetrieveRole("ADMIN");
        Role studentRole = createOrRetrieveRole("STUDENT");
        Role parentRole = createOrRetrieveRole("PARENT");
        Role teacherRole = createOrRetrieveRole("TEACHER");

        //create admin user if not present
        if (!userRepository.existsByEmail("admin@sas.com")) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@sas.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            //assign admin role
            admin.setRoles(Collections.singletonList(adminRole));
            userRepository.save(admin);
        }

        //create teacher user if not present
        if (!userRepository.existsByEmail("teacher@sas.com")) {
            UserEntity teacher = new UserEntity();
            teacher.setEmail("teacher@sas.com");
            teacher.setPassword(passwordEncoder.encode("teacher"));
            //assign teacher role
            teacher.setRoles(Collections.singletonList(teacherRole));
            userRepository.save(teacher);
        }

        //create student user if not present
        if (!userRepository.existsByEmail("student@sas.com")) {
            UserEntity student = new UserEntity();
            student.setEmail("student@sas.com");
            student.setPassword(passwordEncoder.encode("student"));
            //assign student role
            student.setRoles(Collections.singletonList(studentRole));
            userRepository.save(student);
        }

        //create parent user if not present
        if (!userRepository.existsByEmail("parent@sas.com")) {
            UserEntity parent = new UserEntity();
            parent.setEmail("parent@sas.com");
            parent.setPassword(passwordEncoder.encode("parent"));
            //assign parent role
            parent.setRoles(Collections.singletonList(parentRole));
            userRepository.save(parent);
        }
    }

    private Role createOrRetrieveRole(String roleName) {
        //check if role exists
        return roleRepository.findByName(roleName).orElseGet(() -> {
            //create & save role if not present before
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole);
        });
    }
}