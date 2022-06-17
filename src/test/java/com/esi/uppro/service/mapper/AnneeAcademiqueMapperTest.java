package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnneeAcademiqueMapperTest {

    private AnneeAcademiqueMapper anneeAcademiqueMapper;

    @BeforeEach
    public void setUp() {
        anneeAcademiqueMapper = new AnneeAcademiqueMapperImpl();
    }
}
