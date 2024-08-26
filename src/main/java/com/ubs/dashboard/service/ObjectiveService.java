package com.ubs.dashboard.service;

import com.ubs.dashboard.service.dto.ObjectiveDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ubs.dashboard.domain.Objective}.
 */
public interface ObjectiveService {
    /**
     * Save a objective.
     *
     * @param objectiveDTO the entity to save.
     * @return the persisted entity.
     */
    ObjectiveDTO save(ObjectiveDTO objectiveDTO);

    /**
     * Updates a objective.
     *
     * @param objectiveDTO the entity to update.
     * @return the persisted entity.
     */
    ObjectiveDTO update(ObjectiveDTO objectiveDTO);

    /**
     * Partially updates a objective.
     *
     * @param objectiveDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObjectiveDTO> partialUpdate(ObjectiveDTO objectiveDTO);

    /**
     * Get all the objectives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjectiveDTO> findAll(Pageable pageable);

    /**
     * Get the "id" objective.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObjectiveDTO> findOne(Long id);

    /**
     * Delete the "id" objective.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
