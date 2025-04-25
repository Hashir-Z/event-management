package com.software.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;



@Service

public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() { return repository.findAll(); }
    public User findById(String id) { return repository.findById(id).orElse(null); }
    public User findByEmail(String email) { return repository.findByEmail(email).orElse(null); }
    public User save(User user) { return repository.save(user); }
    public void delete(String id) { repository.deleteById(id); }
}
