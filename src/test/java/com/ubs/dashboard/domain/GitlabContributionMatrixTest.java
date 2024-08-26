package com.ubs.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GitlabContributionMatrixTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GitlabContributionMatrix.class);
        GitlabContributionMatrix gitlabContributionMatrix1 = new GitlabContributionMatrix();
        gitlabContributionMatrix1.setId(1L);
        GitlabContributionMatrix gitlabContributionMatrix2 = new GitlabContributionMatrix();
        gitlabContributionMatrix2.setId(gitlabContributionMatrix1.getId());
        assertThat(gitlabContributionMatrix1).isEqualTo(gitlabContributionMatrix2);
        gitlabContributionMatrix2.setId(2L);
        assertThat(gitlabContributionMatrix1).isNotEqualTo(gitlabContributionMatrix2);
        gitlabContributionMatrix1.setId(null);
        assertThat(gitlabContributionMatrix1).isNotEqualTo(gitlabContributionMatrix2);
    }
}
