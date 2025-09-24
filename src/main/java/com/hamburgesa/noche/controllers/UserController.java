package com.hamburgesa.noche.controllers;

import com.hamburgesa.noche.DTO.UserInfo;
import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserInfo> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            userInfos.add(new UserInfo(user));
        }
        return userInfos;
    }

    @GetMapping("/{id}")
    public UserInfo  getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }




}
