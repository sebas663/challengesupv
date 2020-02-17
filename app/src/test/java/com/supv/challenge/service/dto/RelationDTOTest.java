package com.supv.challenge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.supv.challenge.service.dto.RelationDTO;
import com.supv.challenge.web.rest.TestUtil;

public class RelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationDTO.class);
        RelationDTO relationDTO1 = new RelationDTO();
        relationDTO1.setId(1L);
        RelationDTO relationDTO2 = new RelationDTO();
        assertThat(relationDTO1).isNotEqualTo(relationDTO2);
        relationDTO2.setId(relationDTO1.getId());
        assertThat(relationDTO1).isEqualTo(relationDTO2);
        relationDTO2.setId(2L);
        assertThat(relationDTO1).isNotEqualTo(relationDTO2);
        relationDTO1.setId(null);
        assertThat(relationDTO1).isNotEqualTo(relationDTO2);
    }
}
