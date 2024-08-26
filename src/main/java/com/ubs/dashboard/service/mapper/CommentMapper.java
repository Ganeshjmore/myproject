package com.ubs.dashboard.service.mapper;

import com.ubs.dashboard.domain.Comment;
import com.ubs.dashboard.domain.Objective;
import com.ubs.dashboard.domain.Resource;
import com.ubs.dashboard.service.dto.CommentDTO;
import com.ubs.dashboard.service.dto.ObjectiveDTO;
import com.ubs.dashboard.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    @Mapping(target = "objective", source = "objective", qualifiedByName = "objectiveId")
    CommentDTO toDto(Comment s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);

    @Named("objectiveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ObjectiveDTO toDtoObjectiveId(Objective objective);
}
