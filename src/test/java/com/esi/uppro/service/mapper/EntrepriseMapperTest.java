package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrepriseMapperTest {

    private EntrepriseMapper entrepriseMapper;

    @BeforeEach
    public void setUp() {
        entrepriseMapper = new EntrepriseMapperImpl();
    }
}
