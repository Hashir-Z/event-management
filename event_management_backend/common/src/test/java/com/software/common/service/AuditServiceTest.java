package com.software.common;

import com.software.common.auth.FileTransferPrincipalReadOnly;
import com.software.common.entity.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {

    @InjectMocks
    private AuditService auditService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private FileTransferPrincipalReadOnly fileTransferPrincipalReadOnly;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        auditService = new AuditService(entityManager, fileTransferPrincipalReadOnly);
    }

    @Test
    @DisplayName("To verify that createAt doesn't change, but modifiedAt changes during second invocation ")
    public void testWithAuditFields() throws ParseException, InterruptedException {
        BaseEntity entity = new BaseEntity();
        entity.setId("someUserId");

        when(fileTransferPrincipalReadOnly.getId()).thenReturn("someUserId");

        BaseEntity result = auditService.withAuditFields(entity);
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getCreatedBy());
        assertNotNull(result.getModifiedAt());
        assertNotNull(result.getModifiedBy());

        Thread.sleep(1000);
        BaseEntity result1 = auditService.withAuditFields(result);

        verify(entityManager, atLeast(1)).find(BaseEntity.class,entity.getId());
        verify(fileTransferPrincipalReadOnly,atLeast(1)).getId();

        assertEquals(result.getCreatedBy(), "someUserId");
        assertEquals(result.getModifiedBy(), "someUserId");
        assertEquals(result.getCreatedAt(),result1.getCreatedAt());
        assertNotEquals(result.getModifiedAt(),result1.getModifiedAt());
    }

}
