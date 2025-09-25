package com.hamburgesa.noche.controllers;

import com.hamburgesa.noche.entities.Event;
import com.hamburgesa.noche.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id).orElse(null);
    }
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }
    @PutMapping("/update")
    public Event updateEvent(@RequestBody Event event) {
        return eventRepository.save(event);

    }
    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventRepository.deleteById(id);
    }

}
