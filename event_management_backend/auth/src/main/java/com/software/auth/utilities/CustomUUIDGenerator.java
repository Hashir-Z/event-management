package com.software.auth.utilities;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class CustomUUIDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID().toString();
    }
}