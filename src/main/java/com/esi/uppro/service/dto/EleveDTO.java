package com.esi.uppro.service.dto;

import com.esi.uppro.domain.enumeration.Sexe;
import com.esi.uppro.domain.enumeration.SituationMatrimoniale;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Eleve} entity.
 */
public class EleveDTO implements Serializable {

    private Long id;

    @NotNull
    private String matricule;

    @NotNull
    private String nom;

    @NotNull
    private String prenoms;

    private Sexe sexe;

    @NotNull
    private SituationMatrimoniale situationMatrimoniale;

    private LocalDate dateParcoursDebut;

    private LocalDate dateParcoursFin;

    private EncadreurDTO encadreur;

    private ProjetDTO projet;

    private SpecialiteDTO specialite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public LocalDate getDateParcoursDebut() {
        return dateParcoursDebut;
    }

    public void setDateParcoursDebut(LocalDate dateParcoursDebut) {
        this.dateParcoursDebut = dateParcoursDebut;
    }

    public LocalDate getDateParcoursFin() {
        return dateParcoursFin;
    }

    public void setDateParcoursFin(LocalDate dateParcoursFin) {
        this.dateParcoursFin = dateParcoursFin;
    }

    public EncadreurDTO getEncadreur() {
        return encadreur;
    }

    public void setEncadreur(EncadreurDTO encadreur) {
        this.encadreur = encadreur;
    }

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }

    public SpecialiteDTO getSpecialite() {
        return specialite;
    }

    public void setSpecialite(SpecialiteDTO specialite) {
        this.specialite = specialite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EleveDTO)) {
            return false;
        }

        EleveDTO eleveDTO = (EleveDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eleveDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", situationMatrimoniale='" + getSituationMatrimoniale() + "'" +
            ", dateParcoursDebut='" + getDateParcoursDebut() + "'" +
            ", dateParcoursFin='" + getDateParcoursFin() + "'" +
            ", encadreur=" + getEncadreur() +
            ", projet=" + getProjet() +
            ", specialite=" + getSpecialite() +
            "}";
    }
}
