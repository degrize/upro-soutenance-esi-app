package com.esi.uppro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoteEntrepriseRapportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteEntrepriseRapport.class);
        NoteEntrepriseRapport noteEntrepriseRapport1 = new NoteEntrepriseRapport();
        noteEntrepriseRapport1.setId(1L);
        NoteEntrepriseRapport noteEntrepriseRapport2 = new NoteEntrepriseRapport();
        noteEntrepriseRapport2.setId(noteEntrepriseRapport1.getId());
        assertThat(noteEntrepriseRapport1).isEqualTo(noteEntrepriseRapport2);
        noteEntrepriseRapport2.setId(2L);
        assertThat(noteEntrepriseRapport1).isNotEqualTo(noteEntrepriseRapport2);
        noteEntrepriseRapport1.setId(null);
        assertThat(noteEntrepriseRapport1).isNotEqualTo(noteEntrepriseRapport2);
    }
}
