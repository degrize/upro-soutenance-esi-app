package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.domain.Genie;
import com.esi.uppro.domain.Jury;
import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import com.esi.uppro.service.dto.GenieDTO;
import com.esi.uppro.service.dto.JuryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jury} and its DTO {@link JuryDTO}.
 */
@Mapper(componentModel = "spring")
public interface JuryMapper extends EntityMapper<JuryDTO, Jury> {
    @Mapping(target = "anneeAcademique", source = "anneeAcademique", qualifiedByName = "anneeAcademiqueAnneeScolaire")
    @Mapping(target = "genie", source = "genie", qualifiedByName = "genieNom")
    JuryDTO toDto(Jury s);

    @Named("anneeAcademiqueAnneeScolaire")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "anneeScolaire", source = "anneeScolaire")
    AnneeAcademiqueDTO toDtoAnneeAcademiqueAnneeScolaire(AnneeAcademique anneeAcademique);

    @Named("genieNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    GenieDTO toDtoGenieNom(Genie genie);
}
