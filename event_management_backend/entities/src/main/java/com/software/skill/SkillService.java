package com.software.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class SkillService {
    @Autowired
    private SkillRepository repository;

    public List<Skill> findAll() { return repository.findAll(); }
    public Skill findById(Integer id) { return repository.findById(id).orElse(null); }
    public Skill save(Skill skill) { return repository.save(skill); }
    public void delete(Integer id) { repository.deleteById(id); }
}