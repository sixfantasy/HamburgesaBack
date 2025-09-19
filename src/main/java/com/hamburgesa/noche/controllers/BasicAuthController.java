package com.hamburgesa.noche.controllers;

import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.repositories.UserRepository;
import com.hamburgesa.noche.utils.JwtTokenUtil;
import com.hamburgesa.noche.DTO.UserPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
    @RestController
    @RequestMapping("/auth")
    public class BasicAuthController {

        @Autowired
        private JwtTokenUtil jwtTokenUtil;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostMapping(path = "/login")
        public ResponseEntity<String> basicauth(Principal principal) {

            final String token = jwtTokenUtil.generateToken(principal.getName());
            return ResponseEntity.ok().body(token);
        }
        @PostMapping(path = "/register")
        public ResponseEntity<String> register(@RequestBody UserPass userPass)
        {
            if (userRepository.findByUsername(userPass.getUsername()) != null) {
                    return ResponseEntity.badRequest().body("Error: Username is already taken!");
                }
                User user = new User(userPass.getUsername(), passwordEncoder.encode(userPass.getPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("User registered successfully!");
        }
}
