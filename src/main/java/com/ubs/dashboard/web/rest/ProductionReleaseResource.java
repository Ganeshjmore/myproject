package com.ubs.dashboard.web.rest;

import com.ubs.dashboard.repository.ProductionReleaseRepository;
import com.ubs.dashboard.service.ProductionReleaseService;
import com.ubs.dashboard.service.dto.ProductionReleaseDTO;
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
 * REST controller for managing {@link com.ubs.dashboard.domain.ProductionRelease}.
 */
@RestController
@RequestMapping("/api")
public class ProductionReleaseResource {

    private final Logger log = LoggerFactory.getLogger(ProductionReleaseResource.class);

    private static final String ENTITY_NAME = "productionRelease";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionReleaseService productionReleaseService;

    private final ProductionReleaseRepository productionReleaseRepository;

    public ProductionReleaseResource(
        ProductionReleaseService productionReleaseService,
        ProductionReleaseRepository productionReleaseRepository
    ) {
        this.productionReleaseService = productionReleaseService;
        this.productionReleaseRepository = productionReleaseRepository;
    }

    /**
     * {@code POST  /production-releases} : Create a new productionRelease.
     *
     * @param productionReleaseDTO the productionReleaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionReleaseDTO, or with status {@code 400 (Bad Request)} if the productionRelease has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-releases")
    public ResponseEntity<ProductionReleaseDTO> createProductionRelease(@Valid @RequestBody ProductionReleaseDTO productionReleaseDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductionRelease : {}", productionReleaseDTO);
        if (productionReleaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionRelease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionReleaseDTO result = productionReleaseService.save(productionReleaseDTO);
        return ResponseEntity
            .created(new URI("/api/production-releases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-releases/:id} : Updates an existing productionRelease.
     *
     * @param id the id of the productionReleaseDTO to save.
     * @param productionReleaseDTO the productionReleaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionReleaseDTO,
     * or with status {@code 400 (Bad Request)} if the productionReleaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionReleaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-releases/{id}")
    public ResponseEntity<ProductionReleaseDTO> updateProductionRelease(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductionReleaseDTO productionReleaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductionRelease : {}, {}", id, productionReleaseDTO);
        if (productionReleaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionReleaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionReleaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductionReleaseDTO result = productionReleaseService.update(productionReleaseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionReleaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /production-releases/:id} : Partial updates given fields of an existing productionRelease, field will ignore if it is null
     *
     * @param id the id of the productionReleaseDTO to save.
     * @param productionReleaseDTO the productionReleaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionReleaseDTO,
     * or with status {@code 400 (Bad Request)} if the productionReleaseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productionReleaseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionReleaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/production-releases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductionReleaseDTO> partialUpdateProductionRelease(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductionReleaseDTO productionReleaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductionRelease partially : {}, {}", id, productionReleaseDTO);
        if (productionReleaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionReleaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionReleaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionReleaseDTO> result = productionReleaseService.partialUpdate(productionReleaseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionReleaseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /production-releases} : get all the productionReleases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionReleases in body.
     */
    @GetMapping("/production-releases")
    public ResponseEntity<List<ProductionReleaseDTO>> getAllProductionReleases(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductionReleases");
        Page<ProductionReleaseDTO> page = productionReleaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-releases/:id} : get the "id" productionRelease.
     *
     * @param id the id of the productionReleaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionReleaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-releases/{id}")
    public ResponseEntity<ProductionReleaseDTO> getProductionRelease(@PathVariable Long id) {
        log.debug("REST request to get ProductionRelease : {}", id);
        Optional<ProductionReleaseDTO> productionReleaseDTO = productionReleaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionReleaseDTO);
    }

    /**
     * {@code DELETE  /production-releases/:id} : delete the "id" productionRelease.
     *
     * @param id the id of the productionReleaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-releases/{id}")
    public ResponseEntity<Void> deleteProductionRelease(@PathVariable Long id) {
        log.debug("REST request to delete ProductionRelease : {}", id);
        productionReleaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
