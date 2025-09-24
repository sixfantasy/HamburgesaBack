package com.hamburgesa.noche.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Event event;
    private String contribution;

    public UserEvent(User user, Event event) {
        this.user = user;
        this.event = event;
        this.contribution = "";
    }
    public UserEvent() {
    }
}
