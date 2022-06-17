package com.esi.uppro.domain;

import com.esi.uppro.domain.enumeration.Sexe;
import com.esi.uppro.domain.enumeration.SituationMatrimoniale;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenoms", nullable = false)
    private String prenoms;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situation_matrimoniale", nullable = false)
    private SituationMatrimoniale situationMatrimoniale;

    @Column(name = "date_parcours_debut")
    private LocalDate dateParcoursDebut;

    @Column(name = "date_parcours_fin")
    private LocalDate dateParcoursFin;

    @ManyToOne
    private Encadreur encadreur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anneeAcademique", "entreprises" }, allowSetters = true)
    private Projet projet;

    @ManyToOne
    private Specialite specialite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eleve id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Eleve matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return this.nom;
    }

    public Eleve nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return this.prenoms;
    }

    public Eleve prenoms(String prenoms) {
        this.setPrenoms(prenoms);
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Eleve sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return this.situationMatrimoniale;
    }

    public Eleve situationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.setSituationMatrimoniale(situationMatrimoniale);
        return this;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public LocalDate getDateParcoursDebut() {
        return this.dateParcoursDebut;
    }

    public Eleve dateParcoursDebut(LocalDate dateParcoursDebut) {
        this.setDateParcoursDebut(dateParcoursDebut);
        return this;
    }

    public void setDateParcoursDebut(LocalDate dateParcoursDebut) {
        this.dateParcoursDebut = dateParcoursDebut;
    }

    public LocalDate getDateParcoursFin() {
        return this.dateParcoursFin;
    }

    public Eleve dateParcoursFin(LocalDate dateParcoursFin) {
        this.setDateParcoursFin(dateParcoursFin);
        return this;
    }

    public void setDateParcoursFin(LocalDate dateParcoursFin) {
        this.dateParcoursFin = dateParcoursFin;
    }

    public Encadreur getEncadreur() {
        return this.encadreur;
    }

    public void setEncadreur(Encadreur encadreur) {
        this.encadreur = encadreur;
    }

    public Eleve encadreur(Encadreur encadreur) {
        this.setEncadreur(encadreur);
        return this;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Eleve projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    public Specialite getSpecialite() {
        return this.specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Eleve specialite(Specialite specialite) {
        this.setSpecialite(specialite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eleve)) {
            return false;
        }
        return id != null && id.equals(((Eleve) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", situationMatrimoniale='" + getSituationMatrimoniale() + "'" +
            ", dateParcoursDebut='" + getDateParcoursDebut() + "'" +
            ", dateParcoursFin='" + getDateParcoursFin() + "'" +
            "}";
    }
}
