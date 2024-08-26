package com.ubs.dashboard.repository;

import com.ubs.dashboard.domain.GitlabContributionMatrix;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GitlabContributionMatrix entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GitlabContributionMatrixRepository extends JpaRepository<GitlabContributionMatrix, Long> {}
