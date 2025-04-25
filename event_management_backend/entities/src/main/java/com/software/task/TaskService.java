package com.software.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task not found with id " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {
        Task task = getTaskById(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setLocation(updatedTask.getLocation());
        task.setUrgencyLevel(updatedTask.getUrgencyLevel());
        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}