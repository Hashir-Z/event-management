package com.software.event_details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class EventDetailsService {
    @Autowired
    private EventDetailsRepository repository;

    public List<EventDetails> findAll() { return repository.findAll(); }
    public EventDetails findById(Integer id) { return repository.findById(id).orElse(null); }
    public EventDetails save(EventDetails event) { return repository.save(event); }
    public void delete(Integer id) { repository.deleteById(id); }
}