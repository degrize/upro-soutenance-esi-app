package com.esi.uppro.domain;

import com.esi.uppro.domain.enumeration.Mention;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Soutenance.
 */
@Entity
@Table(name = "soutenance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Soutenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mention", nullable = false)
    private Mention mention;

    @NotNull
    @Column(name = "note", nullable = false)
    private Double note;

    @NotNull
    @Column(name = "date_du_jour", nullable = false)
    private LocalDate dateDuJour;

    @Column(name = "remarque")
    private String remarque;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @JsonIgnoreProperties(value = { "anneeAcademique", "entreprises" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Projet projet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anneeAcademique", "genie", "soutenances" }, allowSetters = true)
    private Jury jury;

    @ManyToOne
    private AnneeAcademique anneeAcademique;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soutenance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mention getMention() {
        return this.mention;
    }

    public Soutenance mention(Mention mention) {
        this.setMention(mention);
        return this;
    }

    public void setMention(Mention mention) {
        this.mention = mention;
    }

    public Double getNote() {
        return this.note;
    }

    public Soutenance note(Double note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public LocalDate getDateDuJour() {
        return this.dateDuJour;
    }

    public Soutenance dateDuJour(LocalDate dateDuJour) {
        this.setDateDuJour(dateDuJour);
        return this;
    }

    public void setDateDuJour(LocalDate dateDuJour) {
        this.dateDuJour = dateDuJour;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public Soutenance remarque(String remarque) {
        this.setRemarque(remarque);
        return this;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public LocalDate getDateAjout() {
        return this.dateAjout;
    }

    public Soutenance dateAjout(LocalDate dateAjout) {
        this.setDateAjout(dateAjout);
        return this;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public LocalDate getDateModification() {
        return this.dateModification;
    }

    public Soutenance dateModification(LocalDate dateModification) {
        this.setDateModification(dateModification);
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Soutenance projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    public Jury getJury() {
        return this.jury;
    }

    public void setJury(Jury jury) {
        this.jury = jury;
    }

    public Soutenance jury(Jury jury) {
        this.setJury(jury);
        return this;
    }

    public AnneeAcademique getAnneeAcademique() {
        return this.anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Soutenance anneeAcademique(AnneeAcademique anneeAcademique) {
        this.setAnneeAcademique(anneeAcademique);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soutenance)) {
            return false;
        }
        return id != null && id.equals(((Soutenance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soutenance{" +
            "id=" + getId() +
            ", mention='" + getMention() + "'" +
            ", note=" + getNote() +
            ", dateDuJour='" + getDateDuJour() + "'" +
            ", remarque='" + getRemarque() + "'" +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
