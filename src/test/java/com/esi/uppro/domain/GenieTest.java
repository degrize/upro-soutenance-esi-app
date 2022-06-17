package com.esi.uppro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genie.class);
        Genie genie1 = new Genie();
        genie1.setId(1L);
        Genie genie2 = new Genie();
        genie2.setId(genie1.getId());
        assertThat(genie1).isEqualTo(genie2);
        genie2.setId(2L);
        assertThat(genie1).isNotEqualTo(genie2);
        genie1.setId(null);
        assertThat(genie1).isNotEqualTo(genie2);
    }
}
