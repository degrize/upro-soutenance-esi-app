package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.domain.Entreprise;
import com.esi.uppro.domain.Projet;
import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import com.esi.uppro.service.dto.EntrepriseDTO;
import com.esi.uppro.service.dto.ProjetDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projet} and its DTO {@link ProjetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjetMapper extends EntityMapper<ProjetDTO, Projet> {
    @Mapping(target = "anneeAcademique", source = "anneeAcademique", qualifiedByName = "anneeAcademiqueAnneeScolaire")
    @Mapping(target = "entreprises", source = "entreprises", qualifiedByName = "entrepriseNomSet")
    ProjetDTO toDto(Projet s);

    @Mapping(target = "removeEntreprise", ignore = true)
    Projet toEntity(ProjetDTO projetDTO);

    @Named("anneeAcademiqueAnneeScolaire")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "anneeScolaire", source = "anneeScolaire")
    AnneeAcademiqueDTO toDtoAnneeAcademiqueAnneeScolaire(AnneeAcademique anneeAcademique);

    @Named("entrepriseNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    EntrepriseDTO toDtoEntrepriseNom(Entreprise entreprise);

    @Named("entrepriseNomSet")
    default Set<EntrepriseDTO> toDtoEntrepriseNomSet(Set<Entreprise> entreprise) {
        return entreprise.stream().map(this::toDtoEntrepriseNom).collect(Collectors.toSet());
    }
}
