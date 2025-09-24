package com.hamburgesa.noche.DTO;

import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.entities.UserEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfo {
    private String username;
    private String interests;
    private int  age;
    private List<UserEvent> events;

    public UserInfo(User user) {
        this.username = user.getUsername();
        this.interests = user.getInterests();
        this.age = user.getAge();
        this.events = user.getEvents();
    }
    public UserInfo() {

    }
}
