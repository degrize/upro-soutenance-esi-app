package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Eleve;
import com.esi.uppro.domain.Encadreur;
import com.esi.uppro.domain.Projet;
import com.esi.uppro.domain.Specialite;
import com.esi.uppro.service.dto.EleveDTO;
import com.esi.uppro.service.dto.EncadreurDTO;
import com.esi.uppro.service.dto.ProjetDTO;
import com.esi.uppro.service.dto.SpecialiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eleve} and its DTO {@link EleveDTO}.
 */
@Mapper(componentModel = "spring")
public interface EleveMapper extends EntityMapper<EleveDTO, Eleve> {
    @Mapping(target = "encadreur", source = "encadreur", qualifiedByName = "encadreurNom")
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTheme")
    @Mapping(target = "specialite", source = "specialite", qualifiedByName = "specialiteNom")
    EleveDTO toDto(Eleve s);

    @Named("encadreurNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    EncadreurDTO toDtoEncadreurNom(Encadreur encadreur);

    @Named("projetTheme")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "theme", source = "theme")
    ProjetDTO toDtoProjetTheme(Projet projet);

    @Named("specialiteNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    SpecialiteDTO toDtoSpecialiteNom(Specialite specialite);
}
