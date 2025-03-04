package com.software.common.service;

import com.software.common.entity.CommonUserEntity;
import lombok.RequiredArgsConstructor;
import com.software.common.repository.CommonUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalService {

    private final CommonUserRepository commonUserRepository;

    public Optional<CommonUserEntity> getUserDetails(String token) {
        return commonUserRepository.findByToken(token);
    }
}
