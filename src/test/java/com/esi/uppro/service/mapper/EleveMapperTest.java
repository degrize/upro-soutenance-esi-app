package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EleveMapperTest {

    private EleveMapper eleveMapper;

    @BeforeEach
    public void setUp() {
        eleveMapper = new EleveMapperImpl();
    }
}
