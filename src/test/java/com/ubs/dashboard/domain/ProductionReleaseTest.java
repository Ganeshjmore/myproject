package com.ubs.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionReleaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionRelease.class);
        ProductionRelease productionRelease1 = new ProductionRelease();
        productionRelease1.setId(1L);
        ProductionRelease productionRelease2 = new ProductionRelease();
        productionRelease2.setId(productionRelease1.getId());
        assertThat(productionRelease1).isEqualTo(productionRelease2);
        productionRelease2.setId(2L);
        assertThat(productionRelease1).isNotEqualTo(productionRelease2);
        productionRelease1.setId(null);
        assertThat(productionRelease1).isNotEqualTo(productionRelease2);
    }
}
