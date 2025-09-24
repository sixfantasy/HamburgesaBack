package com.hamburgesa.noche.controllers;

import com.hamburgesa.noche.entities.Event;
import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.entities.UserEvent;
import com.hamburgesa.noche.repositories.EventRepository;
import com.hamburgesa.noche.repositories.UserEventRepository;
import com.hamburgesa.noche.repositories.UserRepository;
import com.hamburgesa.noche.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/userEvent")
public class UserEventController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping("/assistants")
    public List<User> getAssistants(@RequestBody Event event) {
        return userEventRepository.findUsersByEvent(event);
    }
    @PostMapping("/assistants")
    public UserEvent addAssistantToEvent(@RequestBody Event event, @RequestHeader("Authorization") String auth) {
        String token =   auth.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        UserEvent userEvent = new UserEvent(user, event);
        return userEventRepository.save(userEvent);
    }
    @PutMapping("/assistants")
    public UserEvent updateAssistantToEvent(@RequestBody UserEvent userEvent) {
        return userEventRepository.save(userEvent);
    }

}
