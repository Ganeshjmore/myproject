package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.Manager;
import com.ubs.dashboard.service.dto.ManagerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manager} and its DTO {@link ManagerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {}
