package com.esi.uppro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoteEntrepriseRapportMapperTest {

    private NoteEntrepriseRapportMapper noteEntrepriseRapportMapper;

    @BeforeEach
    public void setUp() {
        noteEntrepriseRapportMapper = new NoteEntrepriseRapportMapperImpl();
    }
}
