package com.software.userhistory.repository;

import com.software.userhistory.entity.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, String> {

    /**
     * Finds a user's history record by their email.
     * @param email The user's email address.
     * @return Optional of UserHistoryEntity if a match is found, otherwise Optional.empty().
     */
    @Query(value = "SELECT * FROM user_history WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<UserHistoryEntity> findByEmail(@Param("email") String email);

    /**
     * Retrieves a user's history record based on a valid token while ensuring it is valid
     * (not expired or revoked).
     *
     * @param token The authentication token.
     * @return UserHistoryEntity representing the user history record associated with the token.
     */
    @Query(value = """
            SELECT uh.* FROM user_history uh 
            INNER JOIN token t ON t.user_id = uh.id 
            WHERE t.expired = false AND t.revoked = false AND t.token = :token
            """, nativeQuery = true)
    UserHistoryEntity findHistoryByToken(@Param("token") String token);

    /**
     * Finds all user history records associated with a specific user ID.
     * @param userId The user ID.
     * @return List of UserHistoryEntity associated with the given user ID.
     */
    List<UserHistoryEntity> findByUserId(Long userId);
}