package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenieMapperTest {

    private GenieMapper genieMapper;

    @BeforeEach
    public void setUp() {
        genieMapper = new GenieMapperImpl();
    }
}
