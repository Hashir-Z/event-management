package com.software.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class PreferenceService {
    @Autowired
    private PreferenceRepository repository;

    public List<Preference> findAll() { return repository.findAll(); }
    public Preference findById(Integer id) { return repository.findById(id).orElse(null); }
    public Preference save(Preference preference) { return repository.save(preference); }
    public void delete(Integer id) { repository.deleteById(id); }
}
