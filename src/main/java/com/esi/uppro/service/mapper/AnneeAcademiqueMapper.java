package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnneeAcademique} and its DTO {@link AnneeAcademiqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnneeAcademiqueMapper extends EntityMapper<AnneeAcademiqueDTO, AnneeAcademique> {}
