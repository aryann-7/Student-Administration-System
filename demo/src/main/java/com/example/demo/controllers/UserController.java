package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.UserEntity;
import com.example.demo.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/delete-account")
    public String listUsers(Model model) {
        //get users
        List<UserEntity> users = userService.getAllUsers(); 
         //add users to model
        model.addAttribute("users", users);
        //return user list view
        return "user_list";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        //fallback to user list after deleting
        return "redirect:/admin/delete-account";
    }
}