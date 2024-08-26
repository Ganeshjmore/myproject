package com.ubs.dashboard.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GitlabContributionMatrixDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GitlabContributionMatrixDTO.class);
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO1 = new GitlabContributionMatrixDTO();
        gitlabContributionMatrixDTO1.setId(1L);
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO2 = new GitlabContributionMatrixDTO();
        assertThat(gitlabContributionMatrixDTO1).isNotEqualTo(gitlabContributionMatrixDTO2);
        gitlabContributionMatrixDTO2.setId(gitlabContributionMatrixDTO1.getId());
        assertThat(gitlabContributionMatrixDTO1).isEqualTo(gitlabContributionMatrixDTO2);
        gitlabContributionMatrixDTO2.setId(2L);
        assertThat(gitlabContributionMatrixDTO1).isNotEqualTo(gitlabContributionMatrixDTO2);
        gitlabContributionMatrixDTO1.setId(null);
        assertThat(gitlabContributionMatrixDTO1).isNotEqualTo(gitlabContributionMatrixDTO2);
    }
}
