package com.esi.uppro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoteEntrepriseRapportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteEntrepriseRapportDTO.class);
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO1 = new NoteEntrepriseRapportDTO();
        noteEntrepriseRapportDTO1.setId(1L);
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO2 = new NoteEntrepriseRapportDTO();
        assertThat(noteEntrepriseRapportDTO1).isNotEqualTo(noteEntrepriseRapportDTO2);
        noteEntrepriseRapportDTO2.setId(noteEntrepriseRapportDTO1.getId());
        assertThat(noteEntrepriseRapportDTO1).isEqualTo(noteEntrepriseRapportDTO2);
        noteEntrepriseRapportDTO2.setId(2L);
        assertThat(noteEntrepriseRapportDTO1).isNotEqualTo(noteEntrepriseRapportDTO2);
        noteEntrepriseRapportDTO1.setId(null);
        assertThat(noteEntrepriseRapportDTO1).isNotEqualTo(noteEntrepriseRapportDTO2);
    }
}
