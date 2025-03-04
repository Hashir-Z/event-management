package com.software.common.repository;

import com.software.common.entity.CommonUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonUserRepository extends JpaRepository<CommonUserEntity, String> {

    @Query(value = "SELECT u.* from users u inner join token t " +
            "on t.user_id = u.id where t.expired = false and t.revoked = false " +
            "and t.token = :token", nativeQuery = true)
    Optional<CommonUserEntity> findByToken(@Param("token") String token);
}
