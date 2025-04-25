package com.software.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    public List<Event> findAll() { return repository.findAll(); }
    public Event findById(Integer id) { return repository.findById(id).orElse(null); }
    public Event save(Event event) { return repository.save(event); }
    public void delete(Integer id) { repository.deleteById(id); }
}