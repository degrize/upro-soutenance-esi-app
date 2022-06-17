package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Entreprise;
import com.esi.uppro.service.dto.EntrepriseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entreprise} and its DTO {@link EntrepriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntrepriseMapper extends EntityMapper<EntrepriseDTO, Entreprise> {}
