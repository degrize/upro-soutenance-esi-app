package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Encadreur;
import com.esi.uppro.service.dto.EncadreurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Encadreur} and its DTO {@link EncadreurDTO}.
 */
@Mapper(componentModel = "spring")
public interface EncadreurMapper extends EntityMapper<EncadreurDTO, Encadreur> {}
