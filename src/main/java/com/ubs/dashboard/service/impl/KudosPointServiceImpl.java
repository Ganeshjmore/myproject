package com.ubs.dashboard.service.impl;

import com.ubs.dashboard.domain.KudosPoint;
import com.ubs.dashboard.repository.KudosPointRepository;
import com.ubs.dashboard.service.KudosPointService;
import com.ubs.dashboard.service.dto.KudosPointDTO;
import com.ubs.dashboard.service.mapper.KudosPointMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KudosPoint}.
 */
@Service
@Transactional
public class KudosPointServiceImpl implements KudosPointService {

    private final Logger log = LoggerFactory.getLogger(KudosPointServiceImpl.class);

    private final KudosPointRepository kudosPointRepository;

    private final KudosPointMapper kudosPointMapper;

    public KudosPointServiceImpl(KudosPointRepository kudosPointRepository, KudosPointMapper kudosPointMapper) {
        this.kudosPointRepository = kudosPointRepository;
        this.kudosPointMapper = kudosPointMapper;
    }

    @Override
    public KudosPointDTO save(KudosPointDTO kudosPointDTO) {
        log.debug("Request to save KudosPoint : {}", kudosPointDTO);
        KudosPoint kudosPoint = kudosPointMapper.toEntity(kudosPointDTO);
        kudosPoint = kudosPointRepository.save(kudosPoint);
        return kudosPointMapper.toDto(kudosPoint);
    }

    @Override
    public KudosPointDTO update(KudosPointDTO kudosPointDTO) {
        log.debug("Request to save KudosPoint : {}", kudosPointDTO);
        KudosPoint kudosPoint = kudosPointMapper.toEntity(kudosPointDTO);
        kudosPoint = kudosPointRepository.save(kudosPoint);
        return kudosPointMapper.toDto(kudosPoint);
    }

    @Override
    public Optional<KudosPointDTO> partialUpdate(KudosPointDTO kudosPointDTO) {
        log.debug("Request to partially update KudosPoint : {}", kudosPointDTO);

        return kudosPointRepository
            .findById(kudosPointDTO.getId())
            .map(existingKudosPoint -> {
                kudosPointMapper.partialUpdate(existingKudosPoint, kudosPointDTO);

                return existingKudosPoint;
            })
            .map(kudosPointRepository::save)
            .map(kudosPointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KudosPointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KudosPoints");
        return kudosPointRepository.findAll(pageable).map(kudosPointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KudosPointDTO> findOne(Long id) {
        log.debug("Request to get KudosPoint : {}", id);
        return kudosPointRepository.findById(id).map(kudosPointMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KudosPoint : {}", id);
        kudosPointRepository.deleteById(id);
    }
}
