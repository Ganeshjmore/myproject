package com.ubs.dashboard.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionReleaseMapperTest {

    private ProductionReleaseMapper productionReleaseMapper;

    @BeforeEach
    public void setUp() {
        productionReleaseMapper = new ProductionReleaseMapperImpl();
    }
}
