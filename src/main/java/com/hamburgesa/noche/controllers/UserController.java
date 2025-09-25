package com.hamburgesa.noche.controllers;

import com.hamburgesa.noche.DTO.UserInfo;
import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.repositories.UserEventRepository;
import com.hamburgesa.noche.repositories.UserRepository;
import com.hamburgesa.noche.utils.JwtTokenUtil;
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

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
    @GetMapping("/token")
    public User getUserFromToken(@RequestHeader("Authorization") String auth){
        String token = auth.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return userRepository.findByUsername(username);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
}
    @DeleteMapping("/delete/{id}")
    public void deleteSelf(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        userEventRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
