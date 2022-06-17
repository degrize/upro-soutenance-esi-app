package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SoutenanceMapperTest {

    private SoutenanceMapper soutenanceMapper;

    @BeforeEach
    public void setUp() {
        soutenanceMapper = new SoutenanceMapperImpl();
    }
}
