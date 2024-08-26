package com.ubs.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KudosPointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KudosPoint.class);
        KudosPoint kudosPoint1 = new KudosPoint();
        kudosPoint1.setId(1L);
        KudosPoint kudosPoint2 = new KudosPoint();
        kudosPoint2.setId(kudosPoint1.getId());
        assertThat(kudosPoint1).isEqualTo(kudosPoint2);
        kudosPoint2.setId(2L);
        assertThat(kudosPoint1).isNotEqualTo(kudosPoint2);
        kudosPoint1.setId(null);
        assertThat(kudosPoint1).isNotEqualTo(kudosPoint2);
    }
}
