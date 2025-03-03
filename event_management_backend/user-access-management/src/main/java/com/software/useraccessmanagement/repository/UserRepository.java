package com.software.useraccessmanagement.repository;

import com.software.useraccessmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query(value = "SELECT * FROM users WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<UserEntity> findByEmail(@Param("email") String username);

    @Query(value = "select u.* from users u inner join token t on t.user_id = u.id" + " where t.expired = false and t.revoked = false and t.token = :token", nativeQuery = true)
    UserEntity findIdByToken(@Param("token") String token);
}