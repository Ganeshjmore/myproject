package com.ubs.dashboard.web.rest;

import com.ubs.dashboard.repository.KudosPointRepository;
import com.ubs.dashboard.service.KudosPointService;
import com.ubs.dashboard.service.dto.KudosPointDTO;
import com.ubs.dashboard.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ubs.dashboard.domain.KudosPoint}.
 */
@RestController
@RequestMapping("/api")
public class KudosPointResource {

    private final Logger log = LoggerFactory.getLogger(KudosPointResource.class);

    private static final String ENTITY_NAME = "kudosPoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KudosPointService kudosPointService;

    private final KudosPointRepository kudosPointRepository;

    public KudosPointResource(KudosPointService kudosPointService, KudosPointRepository kudosPointRepository) {
        this.kudosPointService = kudosPointService;
        this.kudosPointRepository = kudosPointRepository;
    }

    /**
     * {@code POST  /kudos-points} : Create a new kudosPoint.
     *
     * @param kudosPointDTO the kudosPointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kudosPointDTO, or with status {@code 400 (Bad Request)} if the kudosPoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kudos-points")
    public ResponseEntity<KudosPointDTO> createKudosPoint(@Valid @RequestBody KudosPointDTO kudosPointDTO) throws URISyntaxException {
        log.debug("REST request to save KudosPoint : {}", kudosPointDTO);
        if (kudosPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new kudosPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KudosPointDTO result = kudosPointService.save(kudosPointDTO);
        return ResponseEntity
            .created(new URI("/api/kudos-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kudos-points/:id} : Updates an existing kudosPoint.
     *
     * @param id the id of the kudosPointDTO to save.
     * @param kudosPointDTO the kudosPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kudosPointDTO,
     * or with status {@code 400 (Bad Request)} if the kudosPointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kudosPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kudos-points/{id}")
    public ResponseEntity<KudosPointDTO> updateKudosPoint(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KudosPointDTO kudosPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KudosPoint : {}, {}", id, kudosPointDTO);
        if (kudosPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kudosPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kudosPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KudosPointDTO result = kudosPointService.update(kudosPointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kudosPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kudos-points/:id} : Partial updates given fields of an existing kudosPoint, field will ignore if it is null
     *
     * @param id the id of the kudosPointDTO to save.
     * @param kudosPointDTO the kudosPointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kudosPointDTO,
     * or with status {@code 400 (Bad Request)} if the kudosPointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the kudosPointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the kudosPointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kudos-points/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KudosPointDTO> partialUpdateKudosPoint(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KudosPointDTO kudosPointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KudosPoint partially : {}, {}", id, kudosPointDTO);
        if (kudosPointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kudosPointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kudosPointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KudosPointDTO> result = kudosPointService.partialUpdate(kudosPointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kudosPointDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /kudos-points} : get all the kudosPoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kudosPoints in body.
     */
    @GetMapping("/kudos-points")
    public ResponseEntity<List<KudosPointDTO>> getAllKudosPoints(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of KudosPoints");
        Page<KudosPointDTO> page = kudosPointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kudos-points/:id} : get the "id" kudosPoint.
     *
     * @param id the id of the kudosPointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kudosPointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kudos-points/{id}")
    public ResponseEntity<KudosPointDTO> getKudosPoint(@PathVariable Long id) {
        log.debug("REST request to get KudosPoint : {}", id);
        Optional<KudosPointDTO> kudosPointDTO = kudosPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kudosPointDTO);
    }

    /**
     * {@code DELETE  /kudos-points/:id} : delete the "id" kudosPoint.
     *
     * @param id the id of the kudosPointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kudos-points/{id}")
    public ResponseEntity<Void> deleteKudosPoint(@PathVariable Long id) {
        log.debug("REST request to delete KudosPoint : {}", id);
        kudosPointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
