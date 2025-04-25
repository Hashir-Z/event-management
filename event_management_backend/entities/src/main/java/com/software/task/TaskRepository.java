package com.software.task;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUrgencyLevel(Task.UrgencyLevel urgencyLevel);
    List<Task> findByEventId(Integer eventId);
}