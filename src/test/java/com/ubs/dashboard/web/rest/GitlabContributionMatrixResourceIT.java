package com.ubs.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ubs.dashboard.IntegrationTest;
import com.ubs.dashboard.domain.GitlabContributionMatrix;
import com.ubs.dashboard.repository.GitlabContributionMatrixRepository;
import com.ubs.dashboard.service.dto.GitlabContributionMatrixDTO;
import com.ubs.dashboard.service.mapper.GitlabContributionMatrixMapper;
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
 * Integration tests for the {@link GitlabContributionMatrixResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GitlabContributionMatrixResourceIT {

    private static final Integer DEFAULT_TOTAL_COMMITS = 1;
    private static final Integer UPDATED_TOTAL_COMMITS = 2;

    private static final Integer DEFAULT_MERGE_REQUESTS = 1;
    private static final Integer UPDATED_MERGE_REQUESTS = 2;

    private static final Integer DEFAULT_ISSUES_CLOSED = 1;
    private static final Integer UPDATED_ISSUES_CLOSED = 2;

    private static final Integer DEFAULT_CODE_REVIEWS = 1;
    private static final Integer UPDATED_CODE_REVIEWS = 2;

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/gitlab-contribution-matrices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GitlabContributionMatrixRepository gitlabContributionMatrixRepository;

    @Autowired
    private GitlabContributionMatrixMapper gitlabContributionMatrixMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGitlabContributionMatrixMockMvc;

    private GitlabContributionMatrix gitlabContributionMatrix;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GitlabContributionMatrix createEntity(EntityManager em) {
        GitlabContributionMatrix gitlabContributionMatrix = new GitlabContributionMatrix()
            .totalCommits(DEFAULT_TOTAL_COMMITS)
            .mergeRequests(DEFAULT_MERGE_REQUESTS)
            .issuesClosed(DEFAULT_ISSUES_CLOSED)
            .codeReviews(DEFAULT_CODE_REVIEWS)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return gitlabContributionMatrix;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GitlabContributionMatrix createUpdatedEntity(EntityManager em) {
        GitlabContributionMatrix gitlabContributionMatrix = new GitlabContributionMatrix()
            .totalCommits(UPDATED_TOTAL_COMMITS)
            .mergeRequests(UPDATED_MERGE_REQUESTS)
            .issuesClosed(UPDATED_ISSUES_CLOSED)
            .codeReviews(UPDATED_CODE_REVIEWS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return gitlabContributionMatrix;
    }

    @BeforeEach
    public void initTest() {
        gitlabContributionMatrix = createEntity(em);
    }

    @Test
    @Transactional
    void createGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeCreate = gitlabContributionMatrixRepository.findAll().size();
        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);
        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeCreate + 1);
        GitlabContributionMatrix testGitlabContributionMatrix = gitlabContributionMatrixList.get(gitlabContributionMatrixList.size() - 1);
        assertThat(testGitlabContributionMatrix.getTotalCommits()).isEqualTo(DEFAULT_TOTAL_COMMITS);
        assertThat(testGitlabContributionMatrix.getMergeRequests()).isEqualTo(DEFAULT_MERGE_REQUESTS);
        assertThat(testGitlabContributionMatrix.getIssuesClosed()).isEqualTo(DEFAULT_ISSUES_CLOSED);
        assertThat(testGitlabContributionMatrix.getCodeReviews()).isEqualTo(DEFAULT_CODE_REVIEWS);
        assertThat(testGitlabContributionMatrix.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createGitlabContributionMatrixWithExistingId() throws Exception {
        // Create the GitlabContributionMatrix with an existing ID
        gitlabContributionMatrix.setId(1L);
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        int databaseSizeBeforeCreate = gitlabContributionMatrixRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTotalCommitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = gitlabContributionMatrixRepository.findAll().size();
        // set the field null
        gitlabContributionMatrix.setTotalCommits(null);

        // Create the GitlabContributionMatrix, which fails.
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMergeRequestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = gitlabContributionMatrixRepository.findAll().size();
        // set the field null
        gitlabContributionMatrix.setMergeRequests(null);

        // Create the GitlabContributionMatrix, which fails.
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIssuesClosedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gitlabContributionMatrixRepository.findAll().size();
        // set the field null
        gitlabContributionMatrix.setIssuesClosed(null);

        // Create the GitlabContributionMatrix, which fails.
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeReviewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = gitlabContributionMatrixRepository.findAll().size();
        // set the field null
        gitlabContributionMatrix.setCodeReviews(null);

        // Create the GitlabContributionMatrix, which fails.
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gitlabContributionMatrixRepository.findAll().size();
        // set the field null
        gitlabContributionMatrix.setLastUpdated(null);

        // Create the GitlabContributionMatrix, which fails.
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGitlabContributionMatrices() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        // Get all the gitlabContributionMatrixList
        restGitlabContributionMatrixMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gitlabContributionMatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalCommits").value(hasItem(DEFAULT_TOTAL_COMMITS)))
            .andExpect(jsonPath("$.[*].mergeRequests").value(hasItem(DEFAULT_MERGE_REQUESTS)))
            .andExpect(jsonPath("$.[*].issuesClosed").value(hasItem(DEFAULT_ISSUES_CLOSED)))
            .andExpect(jsonPath("$.[*].codeReviews").value(hasItem(DEFAULT_CODE_REVIEWS)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getGitlabContributionMatrix() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        // Get the gitlabContributionMatrix
        restGitlabContributionMatrixMockMvc
            .perform(get(ENTITY_API_URL_ID, gitlabContributionMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gitlabContributionMatrix.getId().intValue()))
            .andExpect(jsonPath("$.totalCommits").value(DEFAULT_TOTAL_COMMITS))
            .andExpect(jsonPath("$.mergeRequests").value(DEFAULT_MERGE_REQUESTS))
            .andExpect(jsonPath("$.issuesClosed").value(DEFAULT_ISSUES_CLOSED))
            .andExpect(jsonPath("$.codeReviews").value(DEFAULT_CODE_REVIEWS))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGitlabContributionMatrix() throws Exception {
        // Get the gitlabContributionMatrix
        restGitlabContributionMatrixMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGitlabContributionMatrix() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();

        // Update the gitlabContributionMatrix
        GitlabContributionMatrix updatedGitlabContributionMatrix = gitlabContributionMatrixRepository
            .findById(gitlabContributionMatrix.getId())
            .get();
        // Disconnect from session so that the updates on updatedGitlabContributionMatrix are not directly saved in db
        em.detach(updatedGitlabContributionMatrix);
        updatedGitlabContributionMatrix
            .totalCommits(UPDATED_TOTAL_COMMITS)
            .mergeRequests(UPDATED_MERGE_REQUESTS)
            .issuesClosed(UPDATED_ISSUES_CLOSED)
            .codeReviews(UPDATED_CODE_REVIEWS)
            .lastUpdated(UPDATED_LAST_UPDATED);
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(updatedGitlabContributionMatrix);

        restGitlabContributionMatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gitlabContributionMatrixDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isOk());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
        GitlabContributionMatrix testGitlabContributionMatrix = gitlabContributionMatrixList.get(gitlabContributionMatrixList.size() - 1);
        assertThat(testGitlabContributionMatrix.getTotalCommits()).isEqualTo(UPDATED_TOTAL_COMMITS);
        assertThat(testGitlabContributionMatrix.getMergeRequests()).isEqualTo(UPDATED_MERGE_REQUESTS);
        assertThat(testGitlabContributionMatrix.getIssuesClosed()).isEqualTo(UPDATED_ISSUES_CLOSED);
        assertThat(testGitlabContributionMatrix.getCodeReviews()).isEqualTo(UPDATED_CODE_REVIEWS);
        assertThat(testGitlabContributionMatrix.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gitlabContributionMatrixDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGitlabContributionMatrixWithPatch() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();

        // Update the gitlabContributionMatrix using partial update
        GitlabContributionMatrix partialUpdatedGitlabContributionMatrix = new GitlabContributionMatrix();
        partialUpdatedGitlabContributionMatrix.setId(gitlabContributionMatrix.getId());

        partialUpdatedGitlabContributionMatrix.issuesClosed(UPDATED_ISSUES_CLOSED);

        restGitlabContributionMatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGitlabContributionMatrix.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGitlabContributionMatrix))
            )
            .andExpect(status().isOk());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
        GitlabContributionMatrix testGitlabContributionMatrix = gitlabContributionMatrixList.get(gitlabContributionMatrixList.size() - 1);
        assertThat(testGitlabContributionMatrix.getTotalCommits()).isEqualTo(DEFAULT_TOTAL_COMMITS);
        assertThat(testGitlabContributionMatrix.getMergeRequests()).isEqualTo(DEFAULT_MERGE_REQUESTS);
        assertThat(testGitlabContributionMatrix.getIssuesClosed()).isEqualTo(UPDATED_ISSUES_CLOSED);
        assertThat(testGitlabContributionMatrix.getCodeReviews()).isEqualTo(DEFAULT_CODE_REVIEWS);
        assertThat(testGitlabContributionMatrix.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateGitlabContributionMatrixWithPatch() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();

        // Update the gitlabContributionMatrix using partial update
        GitlabContributionMatrix partialUpdatedGitlabContributionMatrix = new GitlabContributionMatrix();
        partialUpdatedGitlabContributionMatrix.setId(gitlabContributionMatrix.getId());

        partialUpdatedGitlabContributionMatrix
            .totalCommits(UPDATED_TOTAL_COMMITS)
            .mergeRequests(UPDATED_MERGE_REQUESTS)
            .issuesClosed(UPDATED_ISSUES_CLOSED)
            .codeReviews(UPDATED_CODE_REVIEWS)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restGitlabContributionMatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGitlabContributionMatrix.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGitlabContributionMatrix))
            )
            .andExpect(status().isOk());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
        GitlabContributionMatrix testGitlabContributionMatrix = gitlabContributionMatrixList.get(gitlabContributionMatrixList.size() - 1);
        assertThat(testGitlabContributionMatrix.getTotalCommits()).isEqualTo(UPDATED_TOTAL_COMMITS);
        assertThat(testGitlabContributionMatrix.getMergeRequests()).isEqualTo(UPDATED_MERGE_REQUESTS);
        assertThat(testGitlabContributionMatrix.getIssuesClosed()).isEqualTo(UPDATED_ISSUES_CLOSED);
        assertThat(testGitlabContributionMatrix.getCodeReviews()).isEqualTo(UPDATED_CODE_REVIEWS);
        assertThat(testGitlabContributionMatrix.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gitlabContributionMatrixDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGitlabContributionMatrix() throws Exception {
        int databaseSizeBeforeUpdate = gitlabContributionMatrixRepository.findAll().size();
        gitlabContributionMatrix.setId(count.incrementAndGet());

        // Create the GitlabContributionMatrix
        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGitlabContributionMatrixMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gitlabContributionMatrixDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GitlabContributionMatrix in the database
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGitlabContributionMatrix() throws Exception {
        // Initialize the database
        gitlabContributionMatrixRepository.saveAndFlush(gitlabContributionMatrix);

        int databaseSizeBeforeDelete = gitlabContributionMatrixRepository.findAll().size();

        // Delete the gitlabContributionMatrix
        restGitlabContributionMatrixMockMvc
            .perform(delete(ENTITY_API_URL_ID, gitlabContributionMatrix.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GitlabContributionMatrix> gitlabContributionMatrixList = gitlabContributionMatrixRepository.findAll();
        assertThat(gitlabContributionMatrixList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
