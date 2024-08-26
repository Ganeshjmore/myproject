package com.ubs.dashboard.service;

import com.ubs.dashboard.service.dto.ProductionReleaseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ubs.dashboard.domain.ProductionRelease}.
 */
public interface ProductionReleaseService {
    /**
     * Save a productionRelease.
     *
     * @param productionReleaseDTO the entity to save.
     * @return the persisted entity.
     */
    ProductionReleaseDTO save(ProductionReleaseDTO productionReleaseDTO);

    /**
     * Updates a productionRelease.
     *
     * @param productionReleaseDTO the entity to update.
     * @return the persisted entity.
     */
    ProductionReleaseDTO update(ProductionReleaseDTO productionReleaseDTO);

    /**
     * Partially updates a productionRelease.
     *
     * @param productionReleaseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductionReleaseDTO> partialUpdate(ProductionReleaseDTO productionReleaseDTO);

    /**
     * Get all the productionReleases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductionReleaseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productionRelease.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionReleaseDTO> findOne(Long id);

    /**
     * Delete the "id" productionRelease.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
