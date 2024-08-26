package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.Objective;
import com.ubs.dashboard.domain.Rating;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.ObjectiveDTO;
import com.ubs.dashboard.service.dto.RatingDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    @Mapping(target = "objective", source = "objective", qualifiedByName = "objectiveId")
    RatingDTO toDto(Rating s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);

    @Named("objectiveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObjectiveDTO toDtoObjectiveId(Objective objective);
}
