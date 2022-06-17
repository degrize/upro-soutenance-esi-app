package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Entreprise;
import com.esi.uppro.domain.NoteEntrepriseRapport;
import com.esi.uppro.domain.Projet;
import com.esi.uppro.service.dto.EntrepriseDTO;
import com.esi.uppro.service.dto.NoteEntrepriseRapportDTO;
import com.esi.uppro.service.dto.ProjetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NoteEntrepriseRapport} and its DTO {@link NoteEntrepriseRapportDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteEntrepriseRapportMapper extends EntityMapper<NoteEntrepriseRapportDTO, NoteEntrepriseRapport> {
    @Mapping(target = "entreprise", source = "entreprise", qualifiedByName = "entrepriseNom")
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTheme")
    NoteEntrepriseRapportDTO toDto(NoteEntrepriseRapport s);

    @Named("entrepriseNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    EntrepriseDTO toDtoEntrepriseNom(Entreprise entreprise);

    @Named("projetTheme")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "theme", source = "theme")
    ProjetDTO toDtoProjetTheme(Projet projet);
}
