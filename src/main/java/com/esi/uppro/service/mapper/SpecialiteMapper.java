package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Specialite;
import com.esi.uppro.service.dto.SpecialiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialite} and its DTO {@link SpecialiteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecialiteMapper extends EntityMapper<SpecialiteDTO, Specialite> {}
