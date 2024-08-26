package com.ubs.dashboard.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionReleaseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionReleaseDTO.class);
        ProductionReleaseDTO productionReleaseDTO1 = new ProductionReleaseDTO();
        productionReleaseDTO1.setId(1L);
        ProductionReleaseDTO productionReleaseDTO2 = new ProductionReleaseDTO();
        assertThat(productionReleaseDTO1).isNotEqualTo(productionReleaseDTO2);
        productionReleaseDTO2.setId(productionReleaseDTO1.getId());
        assertThat(productionReleaseDTO1).isEqualTo(productionReleaseDTO2);
        productionReleaseDTO2.setId(2L);
        assertThat(productionReleaseDTO1).isNotEqualTo(productionReleaseDTO2);
        productionReleaseDTO1.setId(null);
        assertThat(productionReleaseDTO1).isNotEqualTo(productionReleaseDTO2);
    }
}
