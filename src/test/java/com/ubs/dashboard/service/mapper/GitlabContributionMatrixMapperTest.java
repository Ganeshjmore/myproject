package com.ubs.dashboard.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GitlabContributionMatrixMapperTest {

    private GitlabContributionMatrixMapper gitlabContributionMatrixMapper;

    @BeforeEach
    public void setUp() {
        gitlabContributionMatrixMapper = new GitlabContributionMatrixMapperImpl();
    }
}
