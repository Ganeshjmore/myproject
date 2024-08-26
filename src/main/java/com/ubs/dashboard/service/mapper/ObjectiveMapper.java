package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.Objective;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.ObjectiveDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Objective} and its DTO {@link ObjectiveDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObjectiveMapper extends EntityMapper<ObjectiveDTO, Objective> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    ObjectiveDTO toDto(Objective s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
