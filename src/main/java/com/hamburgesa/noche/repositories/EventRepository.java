package com.hamburgesa.noche.repositories;

import com.hamburgesa.noche.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventRepository extends JpaRepository<Event, Integer> {
}
