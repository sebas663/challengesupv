package com.supv.challenge.service.mapper;


import org.mapstruct.Mapper;

import com.supv.challenge.domain.Relation;
import com.supv.challenge.service.dto.RelationDTO;

/**
 * Mapper for the entity {@link Relation} and its DTO {@link RelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelationMapper extends EntityMapper<RelationDTO, Relation> {



    default Relation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Relation relation = new Relation();
        relation.setId(id);
        return relation;
    }
}
