package com.ubs.dashboard.service;

import com.ubs.dashboard.service.dto.GitlabContributionMatrixDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ubs.dashboard.domain.GitlabContributionMatrix}.
 */
public interface GitlabContributionMatrixService {
    /**
     * Save a gitlabContributionMatrix.
     *
     * @param gitlabContributionMatrixDTO the entity to save.
     * @return the persisted entity.
     */
    GitlabContributionMatrixDTO save(GitlabContributionMatrixDTO gitlabContributionMatrixDTO);

    /**
     * Updates a gitlabContributionMatrix.
     *
     * @param gitlabContributionMatrixDTO the entity to update.
     * @return the persisted entity.
     */
    GitlabContributionMatrixDTO update(GitlabContributionMatrixDTO gitlabContributionMatrixDTO);

    /**
     * Partially updates a gitlabContributionMatrix.
     *
     * @param gitlabContributionMatrixDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GitlabContributionMatrixDTO> partialUpdate(GitlabContributionMatrixDTO gitlabContributionMatrixDTO);

    /**
     * Get all the gitlabContributionMatrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GitlabContributionMatrixDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gitlabContributionMatrix.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GitlabContributionMatrixDTO> findOne(Long id);

    /**
     * Delete the "id" gitlabContributionMatrix.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
