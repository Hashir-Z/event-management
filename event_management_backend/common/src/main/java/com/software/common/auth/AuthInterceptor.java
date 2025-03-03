package com.software.common.auth;

import com.software.common.entity.CommonUserEntity;
import com.software.common.service.PrincipalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final EventManagementPrincipal eventManagementPrincipal;
    private final PrincipalService principalService;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) return true;

        String token = bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;

        Optional<CommonUserEntity> userEntity = principalService.getUserDetails(token);

        if (ObjectUtils.isEmpty(userEntity)) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }

        if(userEntity.isPresent()) {
            eventManagementPrincipal.setId(userEntity.get().getId());
            eventManagementPrincipal.setEmail(userEntity.get().getEmail());
            eventManagementPrincipal.setFullName(userEntity.get().getFullName());
            eventManagementPrincipal.setAdmin(userEntity.get().getIsAdmin());
            return true;
        }
        return false;
    }
}
