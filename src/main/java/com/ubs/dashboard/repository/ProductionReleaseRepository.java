package com.ubs.dashboard.repository;

import com.ubs.dashboard.domain.ProductionRelease;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductionRelease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionReleaseRepository extends JpaRepository<ProductionRelease, Long> {}
