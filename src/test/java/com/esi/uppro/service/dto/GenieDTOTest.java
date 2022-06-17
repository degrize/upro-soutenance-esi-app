package com.esi.uppro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenieDTO.class);
        GenieDTO genieDTO1 = new GenieDTO();
        genieDTO1.setId(1L);
        GenieDTO genieDTO2 = new GenieDTO();
        assertThat(genieDTO1).isNotEqualTo(genieDTO2);
        genieDTO2.setId(genieDTO1.getId());
        assertThat(genieDTO1).isEqualTo(genieDTO2);
        genieDTO2.setId(2L);
        assertThat(genieDTO1).isNotEqualTo(genieDTO2);
        genieDTO1.setId(null);
        assertThat(genieDTO1).isNotEqualTo(genieDTO2);
    }
}
