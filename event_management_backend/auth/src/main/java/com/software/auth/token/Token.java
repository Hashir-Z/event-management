package com.software.auth.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.software.auth.entity.BaseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token extends BaseEntity {

    @Column(name = "user_id")
    public String userId;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;
}
