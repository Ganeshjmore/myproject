package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.ProductionRelease;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.ProductionReleaseDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionRelease} and its DTO {@link ProductionReleaseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductionReleaseMapper extends EntityMapper<ProductionReleaseDTO, ProductionRelease> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    ProductionReleaseDTO toDto(ProductionRelease s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
