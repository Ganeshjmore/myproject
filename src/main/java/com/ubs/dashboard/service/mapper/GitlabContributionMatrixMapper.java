package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.GitlabContributionMatrix;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.GitlabContributionMatrixDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GitlabContributionMatrix} and its DTO {@link GitlabContributionMatrixDTO}.
 */
@Mapper(componentModel = "spring")
public interface GitlabContributionMatrixMapper extends EntityMapper<GitlabContributionMatrixDTO, GitlabContributionMatrix> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    GitlabContributionMatrixDTO toDto(GitlabContributionMatrix s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
