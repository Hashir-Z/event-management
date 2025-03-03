package com.software.clients.uam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "user-access-management",
        url = "${clients.uam.url}")
public interface UserAccessManagementClient {

    @GetMapping("/uam/user")
    Object getUser (@RequestParam(name = "token") String token);

    @PostMapping(path = "/uam/save")
    void addUser(UserAccessManagementRequest request);

    @PostMapping(path = "/uam/email")
    Optional<UserDetailsRecord> findByEmail(String email);
}
