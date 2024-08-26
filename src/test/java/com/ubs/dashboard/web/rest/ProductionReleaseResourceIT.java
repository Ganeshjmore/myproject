package com.ubs.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ubs.dashboard.IntegrationTest;
import com.ubs.dashboard.domain.ProductionRelease;
import com.ubs.dashboard.repository.ProductionReleaseRepository;
import com.ubs.dashboard.service.dto.ProductionReleaseDTO;
import com.ubs.dashboard.service.mapper.ProductionReleaseMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductionReleaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductionReleaseResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_RELEASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_APPLICATION = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/production-releases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductionReleaseRepository productionReleaseRepository;

    @Autowired
    private ProductionReleaseMapper productionReleaseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionReleaseMockMvc;

    private ProductionRelease productionRelease;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionRelease createEntity(EntityManager em) {
        ProductionRelease productionRelease = new ProductionRelease()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .application(DEFAULT_APPLICATION);
        return productionRelease;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionRelease createUpdatedEntity(EntityManager em) {
        ProductionRelease productionRelease = new ProductionRelease()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .releaseDate(UPDATED_RELEASE_DATE)
            .application(UPDATED_APPLICATION);
        return productionRelease;
    }

    @BeforeEach
    public void initTest() {
        productionRelease = createEntity(em);
    }

    @Test
    @Transactional
    void createProductionRelease() throws Exception {
        int databaseSizeBeforeCreate = productionReleaseRepository.findAll().size();
        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);
        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionRelease testProductionRelease = productionReleaseList.get(productionReleaseList.size() - 1);
        assertThat(testProductionRelease.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductionRelease.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductionRelease.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testProductionRelease.getApplication()).isEqualTo(DEFAULT_APPLICATION);
    }

    @Test
    @Transactional
    void createProductionReleaseWithExistingId() throws Exception {
        // Create the ProductionRelease with an existing ID
        productionRelease.setId(1L);
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        int databaseSizeBeforeCreate = productionReleaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionReleaseRepository.findAll().size();
        // set the field null
        productionRelease.setTitle(null);

        // Create the ProductionRelease, which fails.
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionReleaseRepository.findAll().size();
        // set the field null
        productionRelease.setDescription(null);

        // Create the ProductionRelease, which fails.
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReleaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionReleaseRepository.findAll().size();
        // set the field null
        productionRelease.setReleaseDate(null);

        // Create the ProductionRelease, which fails.
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplicationIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionReleaseRepository.findAll().size();
        // set the field null
        productionRelease.setApplication(null);

        // Create the ProductionRelease, which fails.
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        restProductionReleaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductionReleases() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        // Get all the productionReleaseList
        restProductionReleaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionRelease.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].application").value(hasItem(DEFAULT_APPLICATION)));
    }

    @Test
    @Transactional
    void getProductionRelease() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        // Get the productionRelease
        restProductionReleaseMockMvc
            .perform(get(ENTITY_API_URL_ID, productionRelease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionRelease.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.application").value(DEFAULT_APPLICATION));
    }

    @Test
    @Transactional
    void getNonExistingProductionRelease() throws Exception {
        // Get the productionRelease
        restProductionReleaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductionRelease() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();

        // Update the productionRelease
        ProductionRelease updatedProductionRelease = productionReleaseRepository.findById(productionRelease.getId()).get();
        // Disconnect from session so that the updates on updatedProductionRelease are not directly saved in db
        em.detach(updatedProductionRelease);
        updatedProductionRelease
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .releaseDate(UPDATED_RELEASE_DATE)
            .application(UPDATED_APPLICATION);
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(updatedProductionRelease);

        restProductionReleaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionReleaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
        ProductionRelease testProductionRelease = productionReleaseList.get(productionReleaseList.size() - 1);
        assertThat(testProductionRelease.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductionRelease.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductionRelease.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProductionRelease.getApplication()).isEqualTo(UPDATED_APPLICATION);
    }

    @Test
    @Transactional
    void putNonExistingProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionReleaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductionReleaseWithPatch() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();

        // Update the productionRelease using partial update
        ProductionRelease partialUpdatedProductionRelease = new ProductionRelease();
        partialUpdatedProductionRelease.setId(productionRelease.getId());

        partialUpdatedProductionRelease.description(UPDATED_DESCRIPTION).releaseDate(UPDATED_RELEASE_DATE);

        restProductionReleaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionRelease.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionRelease))
            )
            .andExpect(status().isOk());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
        ProductionRelease testProductionRelease = productionReleaseList.get(productionReleaseList.size() - 1);
        assertThat(testProductionRelease.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductionRelease.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductionRelease.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProductionRelease.getApplication()).isEqualTo(DEFAULT_APPLICATION);
    }

    @Test
    @Transactional
    void fullUpdateProductionReleaseWithPatch() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();

        // Update the productionRelease using partial update
        ProductionRelease partialUpdatedProductionRelease = new ProductionRelease();
        partialUpdatedProductionRelease.setId(productionRelease.getId());

        partialUpdatedProductionRelease
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .releaseDate(UPDATED_RELEASE_DATE)
            .application(UPDATED_APPLICATION);

        restProductionReleaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionRelease.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionRelease))
            )
            .andExpect(status().isOk());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
        ProductionRelease testProductionRelease = productionReleaseList.get(productionReleaseList.size() - 1);
        assertThat(testProductionRelease.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductionRelease.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductionRelease.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProductionRelease.getApplication()).isEqualTo(UPDATED_APPLICATION);
    }

    @Test
    @Transactional
    void patchNonExistingProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productionReleaseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductionRelease() throws Exception {
        int databaseSizeBeforeUpdate = productionReleaseRepository.findAll().size();
        productionRelease.setId(count.incrementAndGet());

        // Create the ProductionRelease
        ProductionReleaseDTO productionReleaseDTO = productionReleaseMapper.toDto(productionRelease);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionReleaseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionReleaseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionRelease in the database
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductionRelease() throws Exception {
        // Initialize the database
        productionReleaseRepository.saveAndFlush(productionRelease);

        int databaseSizeBeforeDelete = productionReleaseRepository.findAll().size();

        // Delete the productionRelease
        restProductionReleaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, productionRelease.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionRelease> productionReleaseList = productionReleaseRepository.findAll();
        assertThat(productionReleaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
