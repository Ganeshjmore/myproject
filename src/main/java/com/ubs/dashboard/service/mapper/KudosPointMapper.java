package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.KudosPoint;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.KudosPointDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KudosPoint} and its DTO {@link KudosPointDTO}.
 */
@Mapper(componentModel = "spring")
public interface KudosPointMapper extends EntityMapper<KudosPointDTO, KudosPoint> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    KudosPointDTO toDto(KudosPoint s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
