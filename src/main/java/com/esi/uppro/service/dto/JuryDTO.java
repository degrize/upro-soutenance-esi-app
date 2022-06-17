package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Jury} entity.
 */
public class JuryDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomPresident;

    @NotNull
    private String nomRapporteur;

    @NotNull
    private String nomProfAnglais;

    private String numeroSalle;

    private AnneeAcademiqueDTO anneeAcademique;

    private GenieDTO genie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPresident() {
        return nomPresident;
    }

    public void setNomPresident(String nomPresident) {
        this.nomPresident = nomPresident;
    }

    public String getNomRapporteur() {
        return nomRapporteur;
    }

    public void setNomRapporteur(String nomRapporteur) {
        this.nomRapporteur = nomRapporteur;
    }

    public String getNomProfAnglais() {
        return nomProfAnglais;
    }

    public void setNomProfAnglais(String nomProfAnglais) {
        this.nomProfAnglais = nomProfAnglais;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public AnneeAcademiqueDTO getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademiqueDTO anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public GenieDTO getGenie() {
        return genie;
    }

    public void setGenie(GenieDTO genie) {
        this.genie = genie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JuryDTO)) {
            return false;
        }

        JuryDTO juryDTO = (JuryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, juryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JuryDTO{" +
            "id=" + getId() +
            ", nomPresident='" + getNomPresident() + "'" +
            ", nomRapporteur='" + getNomRapporteur() + "'" +
            ", nomProfAnglais='" + getNomProfAnglais() + "'" +
            ", numeroSalle='" + getNumeroSalle() + "'" +
            ", anneeAcademique=" + getAnneeAcademique() +
            ", genie=" + getGenie() +
            "}";
    }
}
