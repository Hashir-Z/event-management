package com.software.userhistory.service;

import com.software.userhistory.dto.UserHistoryRequest;
import com.software.userhistory.dto.UserHistoryResponse;
import com.software.userhistory.entity.UserHistoryEntity;
import com.software.userhistory.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserHistoryService {

    private final UserHistoryRepository repository;

    @Autowired
    public UserHistoryService(UserHistoryRepository repository) {
        this.repository = repository;
    }

    public UserHistoryResponse logUserEvent(UserHistoryRequest request) {
        // Create a new UserHistoryEntity using values from the request
        UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
        userHistoryEntity.setUserId(request.getUserId()); // userId is now Long
        userHistoryEntity.setAction(request.getAction());
        userHistoryEntity.setTimestamp(request.getTimestamp());

        // Save the entity and map it to a response DTO
        UserHistoryEntity savedEntity = repository.save(userHistoryEntity);
        return new UserHistoryResponse(
                savedEntity.getId(), // id is now Long
                savedEntity.getUserId(),
                savedEntity.getAction(),
                savedEntity.getTimestamp()
        );
    }

    public List<UserHistoryResponse> getUserHistory(Long userId) {
        // Fetch user histories by userId and map them to response DTOs
        return repository.findByUserId(userId)
                .stream()
                .map(history -> new UserHistoryResponse(
                        history.getId(), // id is now Long
                        history.getUserId(),
                        history.getAction(),
                        history.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}