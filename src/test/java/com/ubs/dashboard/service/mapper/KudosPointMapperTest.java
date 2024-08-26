package com.ubs.dashboard.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KudosPointMapperTest {

    private KudosPointMapper kudosPointMapper;

    @BeforeEach
    public void setUp() {
        kudosPointMapper = new KudosPointMapperImpl();
    }
}
