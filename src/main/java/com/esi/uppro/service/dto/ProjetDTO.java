package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Projet} entity.
 */
public class ProjetDTO implements Serializable {

    private Long id;

    @NotNull
    private String theme;

    @Lob
    private byte[] rapport;

    private String rapportContentType;
    private Integer cout;

    private LocalDate dateAjout;

    private LocalDate dateModification;

    private AnneeAcademiqueDTO anneeAcademique;

    private Set<EntrepriseDTO> entreprises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public byte[] getRapport() {
        return rapport;
    }

    public void setRapport(byte[] rapport) {
        this.rapport = rapport;
    }

    public String getRapportContentType() {
        return rapportContentType;
    }

    public void setRapportContentType(String rapportContentType) {
        this.rapportContentType = rapportContentType;
    }

    public Integer getCout() {
        return cout;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public AnneeAcademiqueDTO getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademiqueDTO anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Set<EntrepriseDTO> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(Set<EntrepriseDTO> entreprises) {
        this.entreprises = entreprises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjetDTO)) {
            return false;
        }

        ProjetDTO projetDTO = (ProjetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjetDTO{" +
            "id=" + getId() +
            ", theme='" + getTheme() + "'" +
            ", rapport='" + getRapport() + "'" +
            ", cout=" + getCout() +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            ", anneeAcademique=" + getAnneeAcademique() +
            ", entreprises=" + getEntreprises() +
            "}";
    }
}
