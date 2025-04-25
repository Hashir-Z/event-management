package com.software.availability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository repository;

    public List<Availability> findAll() { return repository.findAll(); }
    public Availability findById(Integer id) { return repository.findById(id).orElse(null); }
    public Availability save(Availability availability) { return repository.save(availability); }
    public void delete(Integer id) { repository.deleteById(id); }
}