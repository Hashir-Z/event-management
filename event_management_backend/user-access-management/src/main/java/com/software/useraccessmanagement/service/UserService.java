package com.software.useraccessmanagement.service;

import lombok.RequiredArgsConstructor;
import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserResponse;
import com.software.useraccessmanagement.entity.UserEntity;
import com.software.useraccessmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${config.okta.user.route}")
    private String oktaUserRoute;

    public Object getUser (final String token) {
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(oktaUserRoute, HttpMethod.GET, jwtEntity, Object.class);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public void addUser(final UserAccessManagementRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(request.password())
                .isAdmin(request.isAdmin())
                .build();
        userRepository.saveAndFlush(userEntity);
    }

    public Optional<UserResponse> findByEmail(final String email) {
        final UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        UserResponse user = null;
        if (userEntity != null) {
            user = new UserResponse(userEntity.getId(),
                    userEntity.getFullName(),
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getIsAdmin());
        }
        assert user != null;
        return Optional.of(user);
    }
}