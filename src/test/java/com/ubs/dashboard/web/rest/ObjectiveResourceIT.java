package com.ubs.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ubs.dashboard.IntegrationTest;
import com.ubs.dashboard.domain.Objective;
import com.ubs.dashboard.domain.enumeration.ObjectiveStatus;
import com.ubs.dashboard.repository.ObjectiveRepository;
import com.ubs.dashboard.service.dto.ObjectiveDTO;
import com.ubs.dashboard.service.mapper.ObjectiveMapper;
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
 * Integration tests for the {@link ObjectiveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObjectiveResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ObjectiveStatus DEFAULT_STATUS = ObjectiveStatus.PENDING;
    private static final ObjectiveStatus UPDATED_STATUS = ObjectiveStatus.IN_PROGRESS;

    private static final String ENTITY_API_URL = "/api/objectives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjectiveMockMvc;

    private Objective objective;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objective createEntity(EntityManager em) {
        Objective objective = new Objective()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .status(DEFAULT_STATUS);
        return objective;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objective createUpdatedEntity(EntityManager em) {
        Objective objective = new Objective()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .status(UPDATED_STATUS);
        return objective;
    }

    @BeforeEach
    public void initTest() {
        objective = createEntity(em);
    }

    @Test
    @Transactional
    void createObjective() throws Exception {
        int databaseSizeBeforeCreate = objectiveRepository.findAll().size();
        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);
        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isCreated());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeCreate + 1);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testObjective.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testObjective.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testObjective.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createObjectiveWithExistingId() throws Exception {
        // Create the Objective with an existing ID
        objective.setId(1L);
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        int databaseSizeBeforeCreate = objectiveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectiveRepository.findAll().size();
        // set the field null
        objective.setTitle(null);

        // Create the Objective, which fails.
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectiveRepository.findAll().size();
        // set the field null
        objective.setDescription(null);

        // Create the Objective, which fails.
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectiveRepository.findAll().size();
        // set the field null
        objective.setCreatedAt(null);

        // Create the Objective, which fails.
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectiveRepository.findAll().size();
        // set the field null
        objective.setStatus(null);

        // Create the Objective, which fails.
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        restObjectiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObjectives() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        // Get all the objectiveList
        restObjectiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objective.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        // Get the objective
        restObjectiveMockMvc
            .perform(get(ENTITY_API_URL_ID, objective.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objective.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingObjective() throws Exception {
        // Get the objective
        restObjectiveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();

        // Update the objective
        Objective updatedObjective = objectiveRepository.findById(objective.getId()).get();
        // Disconnect from session so that the updates on updatedObjective are not directly saved in db
        em.detach(updatedObjective);
        updatedObjective.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).createdAt(UPDATED_CREATED_AT).status(UPDATED_STATUS);
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(updatedObjective);

        restObjectiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isOk());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testObjective.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testObjective.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testObjective.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjectiveWithPatch() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();

        // Update the objective using partial update
        Objective partialUpdatedObjective = new Objective();
        partialUpdatedObjective.setId(objective.getId());

        partialUpdatedObjective.createdAt(UPDATED_CREATED_AT);

        restObjectiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjective.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjective))
            )
            .andExpect(status().isOk());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testObjective.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testObjective.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testObjective.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateObjectiveWithPatch() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();

        // Update the objective using partial update
        Objective partialUpdatedObjective = new Objective();
        partialUpdatedObjective.setId(objective.getId());

        partialUpdatedObjective.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).createdAt(UPDATED_CREATED_AT).status(UPDATED_STATUS);

        restObjectiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjective.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjective))
            )
            .andExpect(status().isOk());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testObjective.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testObjective.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testObjective.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objectiveDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();
        objective.setId(count.incrementAndGet());

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectiveMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(objectiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        int databaseSizeBeforeDelete = objectiveRepository.findAll().size();

        // Delete the objective
        restObjectiveMockMvc
            .perform(delete(ENTITY_API_URL_ID, objective.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
