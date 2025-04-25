package com.software.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository repository;

    public List<Assignment> findAll() { return repository.findAll(); }
    public Assignment findById(Integer id) { return repository.findById(id).orElse(null); }
    public Assignment save(Assignment assignment) { return repository.save(assignment); }
    public void delete(Integer id) { repository.deleteById(id); }
}