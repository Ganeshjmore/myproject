package com.ubs.dashboard.service.impl;

import com.ubs.dashboard.domain.GitlabContributionMatrix;
import com.ubs.dashboard.repository.GitlabContributionMatrixRepository;
import com.ubs.dashboard.service.GitlabContributionMatrixService;
import com.ubs.dashboard.service.dto.GitlabContributionMatrixDTO;
import com.ubs.dashboard.service.mapper.GitlabContributionMatrixMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GitlabContributionMatrix}.
 */
@Service
@Transactional
public class GitlabContributionMatrixServiceImpl implements GitlabContributionMatrixService {

    private final Logger log = LoggerFactory.getLogger(GitlabContributionMatrixServiceImpl.class);

    private final GitlabContributionMatrixRepository gitlabContributionMatrixRepository;

    private final GitlabContributionMatrixMapper gitlabContributionMatrixMapper;

    public GitlabContributionMatrixServiceImpl(
        GitlabContributionMatrixRepository gitlabContributionMatrixRepository,
        GitlabContributionMatrixMapper gitlabContributionMatrixMapper
    ) {
        this.gitlabContributionMatrixRepository = gitlabContributionMatrixRepository;
        this.gitlabContributionMatrixMapper = gitlabContributionMatrixMapper;
    }

    @Override
    public GitlabContributionMatrixDTO save(GitlabContributionMatrixDTO gitlabContributionMatrixDTO) {
        log.debug("Request to save GitlabContributionMatrix : {}", gitlabContributionMatrixDTO);
        GitlabContributionMatrix gitlabContributionMatrix = gitlabContributionMatrixMapper.toEntity(gitlabContributionMatrixDTO);
        gitlabContributionMatrix = gitlabContributionMatrixRepository.save(gitlabContributionMatrix);
        return gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);
    }

    @Override
    public GitlabContributionMatrixDTO update(GitlabContributionMatrixDTO gitlabContributionMatrixDTO) {
        log.debug("Request to save GitlabContributionMatrix : {}", gitlabContributionMatrixDTO);
        GitlabContributionMatrix gitlabContributionMatrix = gitlabContributionMatrixMapper.toEntity(gitlabContributionMatrixDTO);
        gitlabContributionMatrix = gitlabContributionMatrixRepository.save(gitlabContributionMatrix);
        return gitlabContributionMatrixMapper.toDto(gitlabContributionMatrix);
    }

    @Override
    public Optional<GitlabContributionMatrixDTO> partialUpdate(GitlabContributionMatrixDTO gitlabContributionMatrixDTO) {
        log.debug("Request to partially update GitlabContributionMatrix : {}", gitlabContributionMatrixDTO);

        return gitlabContributionMatrixRepository
            .findById(gitlabContributionMatrixDTO.getId())
            .map(existingGitlabContributionMatrix -> {
                gitlabContributionMatrixMapper.partialUpdate(existingGitlabContributionMatrix, gitlabContributionMatrixDTO);

                return existingGitlabContributionMatrix;
            })
            .map(gitlabContributionMatrixRepository::save)
            .map(gitlabContributionMatrixMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GitlabContributionMatrixDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GitlabContributionMatrices");
        return gitlabContributionMatrixRepository.findAll(pageable).map(gitlabContributionMatrixMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GitlabContributionMatrixDTO> findOne(Long id) {
        log.debug("Request to get GitlabContributionMatrix : {}", id);
        return gitlabContributionMatrixRepository.findById(id).map(gitlabContributionMatrixMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GitlabContributionMatrix : {}", id);
        gitlabContributionMatrixRepository.deleteById(id);
    }
}
