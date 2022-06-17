package com.esi.uppro.service.dto;

import com.esi.uppro.domain.enumeration.Mention;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Soutenance} entity.
 */
public class SoutenanceDTO implements Serializable {

    private Long id;

    @NotNull
    private Mention mention;

    @NotNull
    private Double note;

    @NotNull
    private LocalDate dateDuJour;

    private String remarque;

    private LocalDate dateAjout;

    private LocalDate dateModification;

    private ProjetDTO projet;

    private JuryDTO jury;

    private AnneeAcademiqueDTO anneeAcademique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mention getMention() {
        return mention;
    }

    public void setMention(Mention mention) {
        this.mention = mention;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public LocalDate getDateDuJour() {
        return dateDuJour;
    }

    public void setDateDuJour(LocalDate dateDuJour) {
        this.dateDuJour = dateDuJour;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
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

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }

    public JuryDTO getJury() {
        return jury;
    }

    public void setJury(JuryDTO jury) {
        this.jury = jury;
    }

    public AnneeAcademiqueDTO getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademiqueDTO anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoutenanceDTO)) {
            return false;
        }

        SoutenanceDTO soutenanceDTO = (SoutenanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, soutenanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoutenanceDTO{" +
            "id=" + getId() +
            ", mention='" + getMention() + "'" +
            ", note=" + getNote() +
            ", dateDuJour='" + getDateDuJour() + "'" +
            ", remarque='" + getRemarque() + "'" +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            ", projet=" + getProjet() +
            ", jury=" + getJury() +
            ", anneeAcademique=" + getAnneeAcademique() +
            "}";
    }
}
