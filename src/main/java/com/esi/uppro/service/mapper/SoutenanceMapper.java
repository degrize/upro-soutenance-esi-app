package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.domain.Jury;
import com.esi.uppro.domain.Projet;
import com.esi.uppro.domain.Soutenance;
import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import com.esi.uppro.service.dto.JuryDTO;
import com.esi.uppro.service.dto.ProjetDTO;
import com.esi.uppro.service.dto.SoutenanceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Soutenance} and its DTO {@link SoutenanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SoutenanceMapper extends EntityMapper<SoutenanceDTO, Soutenance> {
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTheme")
    @Mapping(target = "jury", source = "jury", qualifiedByName = "juryNomPresident")
    @Mapping(target = "anneeAcademique", source = "anneeAcademique", qualifiedByName = "anneeAcademiqueAnneeScolaire")
    SoutenanceDTO toDto(Soutenance s);

    @Named("projetTheme")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "theme", source = "theme")
    ProjetDTO toDtoProjetTheme(Projet projet);

    @Named("juryNomPresident")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomPresident", source = "nomPresident")
    JuryDTO toDtoJuryNomPresident(Jury jury);

    @Named("anneeAcademiqueAnneeScolaire")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "anneeScolaire", source = "anneeScolaire")
    AnneeAcademiqueDTO toDtoAnneeAcademiqueAnneeScolaire(AnneeAcademique anneeAcademique);
}
