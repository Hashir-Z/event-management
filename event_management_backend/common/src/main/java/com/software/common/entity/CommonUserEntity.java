package com.software.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonUserEntity extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private Boolean isAdmin;
}