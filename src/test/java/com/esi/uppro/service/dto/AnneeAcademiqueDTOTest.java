package com.esi.uppro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esi.uppro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnneeAcademiqueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnneeAcademiqueDTO.class);
        AnneeAcademiqueDTO anneeAcademiqueDTO1 = new AnneeAcademiqueDTO();
        anneeAcademiqueDTO1.setId(1L);
        AnneeAcademiqueDTO anneeAcademiqueDTO2 = new AnneeAcademiqueDTO();
        assertThat(anneeAcademiqueDTO1).isNotEqualTo(anneeAcademiqueDTO2);
        anneeAcademiqueDTO2.setId(anneeAcademiqueDTO1.getId());
        assertThat(anneeAcademiqueDTO1).isEqualTo(anneeAcademiqueDTO2);
        anneeAcademiqueDTO2.setId(2L);
        assertThat(anneeAcademiqueDTO1).isNotEqualTo(anneeAcademiqueDTO2);
        anneeAcademiqueDTO1.setId(null);
        assertThat(anneeAcademiqueDTO1).isNotEqualTo(anneeAcademiqueDTO2);
    }
}
