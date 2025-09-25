package com.hamburgesa.noche.repositories;

import com.hamburgesa.noche.entities.Event;
import com.hamburgesa.noche.entities.User;
import com.hamburgesa.noche.entities.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Integer> {

    List<UserEvent> findByUser(User user);
    List<UserEvent> findByEvent(Event event);

    List<User> findUsersByEvent(Event event);

    void deleteAllByUser(User user);
}
