package com.ubs.dashboard.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KudosPointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KudosPointDTO.class);
        KudosPointDTO kudosPointDTO1 = new KudosPointDTO();
        kudosPointDTO1.setId(1L);
        KudosPointDTO kudosPointDTO2 = new KudosPointDTO();
        assertThat(kudosPointDTO1).isNotEqualTo(kudosPointDTO2);
        kudosPointDTO2.setId(kudosPointDTO1.getId());
        assertThat(kudosPointDTO1).isEqualTo(kudosPointDTO2);
        kudosPointDTO2.setId(2L);
        assertThat(kudosPointDTO1).isNotEqualTo(kudosPointDTO2);
        kudosPointDTO1.setId(null);
        assertThat(kudosPointDTO1).isNotEqualTo(kudosPointDTO2);
    }
}
