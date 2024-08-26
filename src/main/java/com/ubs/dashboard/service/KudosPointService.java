package com.ubs.dashboard.service;

import com.ubs.dashboard.service.dto.KudosPointDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ubs.dashboard.domain.KudosPoint}.
 */
public interface KudosPointService {
    /**
     * Save a kudosPoint.
     *
     * @param kudosPointDTO the entity to save.
     * @return the persisted entity.
     */
    KudosPointDTO save(KudosPointDTO kudosPointDTO);

    /**
     * Updates a kudosPoint.
     *
     * @param kudosPointDTO the entity to update.
     * @return the persisted entity.
     */
    KudosPointDTO update(KudosPointDTO kudosPointDTO);

    /**
     * Partially updates a kudosPoint.
     *
     * @param kudosPointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KudosPointDTO> partialUpdate(KudosPointDTO kudosPointDTO);

    /**
     * Get all the kudosPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KudosPointDTO> findAll(Pageable pageable);

    /**
     * Get the "id" kudosPoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KudosPointDTO> findOne(Long id);

    /**
     * Delete the "id" kudosPoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
