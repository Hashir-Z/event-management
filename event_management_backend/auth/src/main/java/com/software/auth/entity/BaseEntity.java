package com.software.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.software.auth.utilities.CustomUUIDGenerator")
    private String id;

    @Column(name="created_at")
    private Long createdAt;

    @Column(name="modified_at")
    private Long modifiedAt;

    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "modified_by")
    private String modifiedBy;
}
