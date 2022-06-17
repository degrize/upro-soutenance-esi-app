package com.esi.uppro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NoteEntrepriseRapport.
 */
@Entity
@Table(name = "note_entreprise_rapport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoteEntrepriseRapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "note", nullable = false)
    private Double note;

    @Column(name = "observation")
    private String observation;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projets" }, allowSetters = true)
    private Entreprise entreprise;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anneeAcademique", "entreprises" }, allowSetters = true)
    private Projet projet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NoteEntrepriseRapport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return this.note;
    }

    public NoteEntrepriseRapport note(Double note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getObservation() {
        return this.observation;
    }

    public NoteEntrepriseRapport observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDateAjout() {
        return this.dateAjout;
    }

    public NoteEntrepriseRapport dateAjout(LocalDate dateAjout) {
        this.setDateAjout(dateAjout);
        return this;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public LocalDate getDateModification() {
        return this.dateModification;
    }

    public NoteEntrepriseRapport dateModification(LocalDate dateModification) {
        this.setDateModification(dateModification);
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public NoteEntrepriseRapport entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public NoteEntrepriseRapport projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteEntrepriseRapport)) {
            return false;
        }
        return id != null && id.equals(((NoteEntrepriseRapport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteEntrepriseRapport{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", observation='" + getObservation() + "'" +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
