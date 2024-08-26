package com.ubs.dashboard.repository;

import com.ubs.dashboard.domain.KudosPoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KudosPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KudosPointRepository extends JpaRepository<KudosPoint, Long> {}
