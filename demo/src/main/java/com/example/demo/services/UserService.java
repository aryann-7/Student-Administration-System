package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserEntity;
import com.example.demo.repositories.ParentRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<UserEntity> getAllUsers() {
         //get all users
        return userRepository.findAll();
    }

    public void deleteUserById(int id) {
         //delete user by id
        userRepository.deleteById(id);
    }

    public void deleteUserWithRoles(int userId) {
        //find the user by id
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //remove all roles associated with the user
        user.getRoles().clear();

        //save user without roles
        userRepository.save(user);

        //delete the user
        userRepository.deleteById(userId);
    }
    
    @Transactional
    public void deleteUser(int userId) {
        //check if user exists
        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isPresent()) {
            //remove associated roles
            teacherRepository.deleteByUserId(userId);
            studentRepository.deleteByUserId(userId);
            parentRepository.deleteByUserId(userId);
            
            //delete the user
            userRepository.deleteById(userId);
        }
    }
}