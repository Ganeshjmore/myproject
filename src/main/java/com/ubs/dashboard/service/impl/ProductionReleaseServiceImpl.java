package com.ubs.dashboard.service.impl;

import com.ubs.dashboard.domain.ProductionRelease;
import com.ubs.dashboard.repository.ProductionReleaseRepository;
import com.ubs.dashboard.service.ProductionReleaseService;
import com.ubs.dashboard.service.dto.ProductionReleaseDTO;
import com.ubs.dashboard.service.mapper.ProductionReleaseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionRelease}.
 */
@Service
@Transactional
public class ProductionReleaseServiceImpl implements ProductionReleaseService {

    private final Logger log = LoggerFactory.getLogger(ProductionReleaseServiceImpl.class);

    private final ProductionReleaseRepository productionReleaseRepository;

    private final ProductionReleaseMapper productionReleaseMapper;

    public ProductionReleaseServiceImpl(
        ProductionReleaseRepository productionReleaseRepository,
        ProductionReleaseMapper productionReleaseMapper
    ) {
        this.productionReleaseRepository = productionReleaseRepository;
        this.productionReleaseMapper = productionReleaseMapper;
    }

    @Override
    public ProductionReleaseDTO save(ProductionReleaseDTO productionReleaseDTO) {
        log.debug("Request to save ProductionRelease : {}", productionReleaseDTO);
        ProductionRelease productionRelease = productionReleaseMapper.toEntity(productionReleaseDTO);
        productionRelease = productionReleaseRepository.save(productionRelease);
        return productionReleaseMapper.toDto(productionRelease);
    }

    @Override
    public ProductionReleaseDTO update(ProductionReleaseDTO productionReleaseDTO) {
        log.debug("Request to save ProductionRelease : {}", productionReleaseDTO);
        ProductionRelease productionRelease = productionReleaseMapper.toEntity(productionReleaseDTO);
        productionRelease = productionReleaseRepository.save(productionRelease);
        return productionReleaseMapper.toDto(productionRelease);
    }

    @Override
    public Optional<ProductionReleaseDTO> partialUpdate(ProductionReleaseDTO productionReleaseDTO) {
        log.debug("Request to partially update ProductionRelease : {}", productionReleaseDTO);

        return productionReleaseRepository
            .findById(productionReleaseDTO.getId())
            .map(existingProductionRelease -> {
                productionReleaseMapper.partialUpdate(existingProductionRelease, productionReleaseDTO);

                return existingProductionRelease;
            })
            .map(productionReleaseRepository::save)
            .map(productionReleaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductionReleaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductionReleases");
        return productionReleaseRepository.findAll(pageable).map(productionReleaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionReleaseDTO> findOne(Long id) {
        log.debug("Request to get ProductionRelease : {}", id);
        return productionReleaseRepository.findById(id).map(productionReleaseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionRelease : {}", id);
        productionReleaseRepository.deleteById(id);
    }
}
