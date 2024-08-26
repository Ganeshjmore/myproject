package com.ubs.dashboard.web.rest;

import com.ubs.dashboard.repository.GitlabContributionMatrixRepository;
import com.ubs.dashboard.service.GitlabContributionMatrixService;
import com.ubs.dashboard.service.dto.GitlabContributionMatrixDTO;
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
 * REST controller for managing {@link com.ubs.dashboard.domain.GitlabContributionMatrix}.
 */
@RestController
@RequestMapping("/api")
public class GitlabContributionMatrixResource {

    private final Logger log = LoggerFactory.getLogger(GitlabContributionMatrixResource.class);

    private static final String ENTITY_NAME = "gitlabContributionMatrix";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GitlabContributionMatrixService gitlabContributionMatrixService;

    private final GitlabContributionMatrixRepository gitlabContributionMatrixRepository;

    public GitlabContributionMatrixResource(
        GitlabContributionMatrixService gitlabContributionMatrixService,
        GitlabContributionMatrixRepository gitlabContributionMatrixRepository
    ) {
        this.gitlabContributionMatrixService = gitlabContributionMatrixService;
        this.gitlabContributionMatrixRepository = gitlabContributionMatrixRepository;
    }

    /**
     * {@code POST  /gitlab-contribution-matrices} : Create a new gitlabContributionMatrix.
     *
     * @param gitlabContributionMatrixDTO the gitlabContributionMatrixDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gitlabContributionMatrixDTO, or with status {@code 400 (Bad Request)} if the gitlabContributionMatrix has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gitlab-contribution-matrices")
    public ResponseEntity<GitlabContributionMatrixDTO> createGitlabContributionMatrix(
        @Valid @RequestBody GitlabContributionMatrixDTO gitlabContributionMatrixDTO
    ) throws URISyntaxException {
        log.debug("REST request to save GitlabContributionMatrix : {}", gitlabContributionMatrixDTO);
        if (gitlabContributionMatrixDTO.getId() != null) {
            throw new BadRequestAlertException("A new gitlabContributionMatrix cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GitlabContributionMatrixDTO result = gitlabContributionMatrixService.save(gitlabContributionMatrixDTO);
        return ResponseEntity
            .created(new URI("/api/gitlab-contribution-matrices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gitlab-contribution-matrices/:id} : Updates an existing gitlabContributionMatrix.
     *
     * @param id the id of the gitlabContributionMatrixDTO to save.
     * @param gitlabContributionMatrixDTO the gitlabContributionMatrixDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gitlabContributionMatrixDTO,
     * or with status {@code 400 (Bad Request)} if the gitlabContributionMatrixDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gitlabContributionMatrixDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gitlab-contribution-matrices/{id}")
    public ResponseEntity<GitlabContributionMatrixDTO> updateGitlabContributionMatrix(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GitlabContributionMatrixDTO gitlabContributionMatrixDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GitlabContributionMatrix : {}, {}", id, gitlabContributionMatrixDTO);
        if (gitlabContributionMatrixDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gitlabContributionMatrixDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gitlabContributionMatrixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GitlabContributionMatrixDTO result = gitlabContributionMatrixService.update(gitlabContributionMatrixDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gitlabContributionMatrixDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /gitlab-contribution-matrices/:id} : Partial updates given fields of an existing gitlabContributionMatrix, field will ignore if it is null
     *
     * @param id the id of the gitlabContributionMatrixDTO to save.
     * @param gitlabContributionMatrixDTO the gitlabContributionMatrixDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gitlabContributionMatrixDTO,
     * or with status {@code 400 (Bad Request)} if the gitlabContributionMatrixDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gitlabContributionMatrixDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gitlabContributionMatrixDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gitlab-contribution-matrices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GitlabContributionMatrixDTO> partialUpdateGitlabContributionMatrix(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GitlabContributionMatrixDTO gitlabContributionMatrixDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GitlabContributionMatrix partially : {}, {}", id, gitlabContributionMatrixDTO);
        if (gitlabContributionMatrixDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gitlabContributionMatrixDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gitlabContributionMatrixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GitlabContributionMatrixDTO> result = gitlabContributionMatrixService.partialUpdate(gitlabContributionMatrixDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gitlabContributionMatrixDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gitlab-contribution-matrices} : get all the gitlabContributionMatrices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gitlabContributionMatrices in body.
     */
    @GetMapping("/gitlab-contribution-matrices")
    public ResponseEntity<List<GitlabContributionMatrixDTO>> getAllGitlabContributionMatrices(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of GitlabContributionMatrices");
        Page<GitlabContributionMatrixDTO> page = gitlabContributionMatrixService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gitlab-contribution-matrices/:id} : get the "id" gitlabContributionMatrix.
     *
     * @param id the id of the gitlabContributionMatrixDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gitlabContributionMatrixDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gitlab-contribution-matrices/{id}")
    public ResponseEntity<GitlabContributionMatrixDTO> getGitlabContributionMatrix(@PathVariable Long id) {
        log.debug("REST request to get GitlabContributionMatrix : {}", id);
        Optional<GitlabContributionMatrixDTO> gitlabContributionMatrixDTO = gitlabContributionMatrixService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gitlabContributionMatrixDTO);
    }

    /**
     * {@code DELETE  /gitlab-contribution-matrices/:id} : delete the "id" gitlabContributionMatrix.
     *
     * @param id the id of the gitlabContributionMatrixDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gitlab-contribution-matrices/{id}")
    public ResponseEntity<Void> deleteGitlabContributionMatrix(@PathVariable Long id) {
        log.debug("REST request to delete GitlabContributionMatrix : {}", id);
        gitlabContributionMatrixService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
