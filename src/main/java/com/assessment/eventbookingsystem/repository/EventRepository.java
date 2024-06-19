package com.assessment.eventbookingsystem.repository;

import com.assessment.eventbookingsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {
    Optional<Event> findEventById(String id);
    Optional<Event> findEventByName(String name);
}
