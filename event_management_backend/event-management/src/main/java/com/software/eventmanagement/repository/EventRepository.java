package com.software.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.software.eventmanagement.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // You can add custom query methods here if needed
}
