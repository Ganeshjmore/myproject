package com.ubs.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ubs.dashboard.IntegrationTest;
import com.ubs.dashboard.domain.KudosPoint;
import com.ubs.dashboard.repository.KudosPointRepository;
import com.ubs.dashboard.service.dto.KudosPointDTO;
import com.ubs.dashboard.service.mapper.KudosPointMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link KudosPointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KudosPointResourceIT {

    private static final String DEFAULT_GIVEN_BY = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN_BY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kudos-points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KudosPointRepository kudosPointRepository;

    @Autowired
    private KudosPointMapper kudosPointMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKudosPointMockMvc;

    private KudosPoint kudosPoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KudosPoint createEntity(EntityManager em) {
        KudosPoint kudosPoint = new KudosPoint()
            .givenBy(DEFAULT_GIVEN_BY)
            .comments(DEFAULT_COMMENTS)
            .points(DEFAULT_POINTS)
            .category(DEFAULT_CATEGORY);
        return kudosPoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KudosPoint createUpdatedEntity(EntityManager em) {
        KudosPoint kudosPoint = new KudosPoint()
            .givenBy(UPDATED_GIVEN_BY)
            .comments(UPDATED_COMMENTS)
            .points(UPDATED_POINTS)
            .category(UPDATED_CATEGORY);
        return kudosPoint;
    }

    @BeforeEach
    public void initTest() {
        kudosPoint = createEntity(em);
    }

    @Test
    @Transactional
    void createKudosPoint() throws Exception {
        int databaseSizeBeforeCreate = kudosPointRepository.findAll().size();
        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);
        restKudosPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isCreated());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeCreate + 1);
        KudosPoint testKudosPoint = kudosPointList.get(kudosPointList.size() - 1);
        assertThat(testKudosPoint.getGivenBy()).isEqualTo(DEFAULT_GIVEN_BY);
        assertThat(testKudosPoint.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testKudosPoint.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testKudosPoint.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void createKudosPointWithExistingId() throws Exception {
        // Create the KudosPoint with an existing ID
        kudosPoint.setId(1L);
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        int databaseSizeBeforeCreate = kudosPointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKudosPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGivenByIsRequired() throws Exception {
        int databaseSizeBeforeTest = kudosPointRepository.findAll().size();
        // set the field null
        kudosPoint.setGivenBy(null);

        // Create the KudosPoint, which fails.
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        restKudosPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isBadRequest());

        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = kudosPointRepository.findAll().size();
        // set the field null
        kudosPoint.setPoints(null);

        // Create the KudosPoint, which fails.
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        restKudosPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isBadRequest());

        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = kudosPointRepository.findAll().size();
        // set the field null
        kudosPoint.setCategory(null);

        // Create the KudosPoint, which fails.
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        restKudosPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isBadRequest());

        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKudosPoints() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        // Get all the kudosPointList
        restKudosPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kudosPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].givenBy").value(hasItem(DEFAULT_GIVEN_BY)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getKudosPoint() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        // Get the kudosPoint
        restKudosPointMockMvc
            .perform(get(ENTITY_API_URL_ID, kudosPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kudosPoint.getId().intValue()))
            .andExpect(jsonPath("$.givenBy").value(DEFAULT_GIVEN_BY))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingKudosPoint() throws Exception {
        // Get the kudosPoint
        restKudosPointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKudosPoint() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();

        // Update the kudosPoint
        KudosPoint updatedKudosPoint = kudosPointRepository.findById(kudosPoint.getId()).get();
        // Disconnect from session so that the updates on updatedKudosPoint are not directly saved in db
        em.detach(updatedKudosPoint);
        updatedKudosPoint.givenBy(UPDATED_GIVEN_BY).comments(UPDATED_COMMENTS).points(UPDATED_POINTS).category(UPDATED_CATEGORY);
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(updatedKudosPoint);

        restKudosPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kudosPointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isOk());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
        KudosPoint testKudosPoint = kudosPointList.get(kudosPointList.size() - 1);
        assertThat(testKudosPoint.getGivenBy()).isEqualTo(UPDATED_GIVEN_BY);
        assertThat(testKudosPoint.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testKudosPoint.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testKudosPoint.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kudosPointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kudosPointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKudosPointWithPatch() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();

        // Update the kudosPoint using partial update
        KudosPoint partialUpdatedKudosPoint = new KudosPoint();
        partialUpdatedKudosPoint.setId(kudosPoint.getId());

        partialUpdatedKudosPoint.givenBy(UPDATED_GIVEN_BY);

        restKudosPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKudosPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKudosPoint))
            )
            .andExpect(status().isOk());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
        KudosPoint testKudosPoint = kudosPointList.get(kudosPointList.size() - 1);
        assertThat(testKudosPoint.getGivenBy()).isEqualTo(UPDATED_GIVEN_BY);
        assertThat(testKudosPoint.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testKudosPoint.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testKudosPoint.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateKudosPointWithPatch() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();

        // Update the kudosPoint using partial update
        KudosPoint partialUpdatedKudosPoint = new KudosPoint();
        partialUpdatedKudosPoint.setId(kudosPoint.getId());

        partialUpdatedKudosPoint.givenBy(UPDATED_GIVEN_BY).comments(UPDATED_COMMENTS).points(UPDATED_POINTS).category(UPDATED_CATEGORY);

        restKudosPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKudosPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKudosPoint))
            )
            .andExpect(status().isOk());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
        KudosPoint testKudosPoint = kudosPointList.get(kudosPointList.size() - 1);
        assertThat(testKudosPoint.getGivenBy()).isEqualTo(UPDATED_GIVEN_BY);
        assertThat(testKudosPoint.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testKudosPoint.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testKudosPoint.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kudosPointDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKudosPoint() throws Exception {
        int databaseSizeBeforeUpdate = kudosPointRepository.findAll().size();
        kudosPoint.setId(count.incrementAndGet());

        // Create the KudosPoint
        KudosPointDTO kudosPointDTO = kudosPointMapper.toDto(kudosPoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKudosPointMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kudosPointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KudosPoint in the database
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKudosPoint() throws Exception {
        // Initialize the database
        kudosPointRepository.saveAndFlush(kudosPoint);

        int databaseSizeBeforeDelete = kudosPointRepository.findAll().size();

        // Delete the kudosPoint
        restKudosPointMockMvc
            .perform(delete(ENTITY_API_URL_ID, kudosPoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KudosPoint> kudosPointList = kudosPointRepository.findAll();
        assertThat(kudosPointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
