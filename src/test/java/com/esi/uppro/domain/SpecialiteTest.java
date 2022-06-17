package com.esi.uppro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecialiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialite.class);
        Specialite specialite1 = new Specialite();
        specialite1.setId(1L);
        Specialite specialite2 = new Specialite();
        specialite2.setId(specialite1.getId());
        assertThat(specialite1).isEqualTo(specialite2);
        specialite2.setId(2L);
        assertThat(specialite1).isNotEqualTo(specialite2);
        specialite1.setId(null);
        assertThat(specialite1).isNotEqualTo(specialite2);
    }
}
