package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.Manager;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.ManagerDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {
    @Mapping(target = "manager", source = "manager", qualifiedByName = "managerId")
    ResourceDTO toDto(Resource s);

    @Named("managerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManagerDTO toDtoManagerId(Manager manager);
}
