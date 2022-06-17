package com.esi.uppro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jury.
 */
@Entity
@Table(name = "jury")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_president", nullable = false)
    private String nomPresident;

    @NotNull
    @Column(name = "nom_rapporteur", nullable = false)
    private String nomRapporteur;

    @NotNull
    @Column(name = "nom_prof_anglais", nullable = false)
    private String nomProfAnglais;

    @Column(name = "numero_salle")
    private String numeroSalle;

    @ManyToOne
    private AnneeAcademique anneeAcademique;

    @ManyToOne
    private Genie genie;

    @OneToMany(mappedBy = "jury")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projet", "jury", "anneeAcademique" }, allowSetters = true)
    private Set<Soutenance> soutenances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jury id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPresident() {
        return this.nomPresident;
    }

    public Jury nomPresident(String nomPresident) {
        this.setNomPresident(nomPresident);
        return this;
    }

    public void setNomPresident(String nomPresident) {
        this.nomPresident = nomPresident;
    }

    public String getNomRapporteur() {
        return this.nomRapporteur;
    }

    public Jury nomRapporteur(String nomRapporteur) {
        this.setNomRapporteur(nomRapporteur);
        return this;
    }

    public void setNomRapporteur(String nomRapporteur) {
        this.nomRapporteur = nomRapporteur;
    }

    public String getNomProfAnglais() {
        return this.nomProfAnglais;
    }

    public Jury nomProfAnglais(String nomProfAnglais) {
        this.setNomProfAnglais(nomProfAnglais);
        return this;
    }

    public void setNomProfAnglais(String nomProfAnglais) {
        this.nomProfAnglais = nomProfAnglais;
    }

    public String getNumeroSalle() {
        return this.numeroSalle;
    }

    public Jury numeroSalle(String numeroSalle) {
        this.setNumeroSalle(numeroSalle);
        return this;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public AnneeAcademique getAnneeAcademique() {
        return this.anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Jury anneeAcademique(AnneeAcademique anneeAcademique) {
        this.setAnneeAcademique(anneeAcademique);
        return this;
    }

    public Genie getGenie() {
        return this.genie;
    }

    public void setGenie(Genie genie) {
        this.genie = genie;
    }

    public Jury genie(Genie genie) {
        this.setGenie(genie);
        return this;
    }

    public Set<Soutenance> getSoutenances() {
        return this.soutenances;
    }

    public void setSoutenances(Set<Soutenance> soutenances) {
        if (this.soutenances != null) {
            this.soutenances.forEach(i -> i.setJury(null));
        }
        if (soutenances != null) {
            soutenances.forEach(i -> i.setJury(this));
        }
        this.soutenances = soutenances;
    }

    public Jury soutenances(Set<Soutenance> soutenances) {
        this.setSoutenances(soutenances);
        return this;
    }

    public Jury addSoutenance(Soutenance soutenance) {
        this.soutenances.add(soutenance);
        soutenance.setJury(this);
        return this;
    }

    public Jury removeSoutenance(Soutenance soutenance) {
        this.soutenances.remove(soutenance);
        soutenance.setJury(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jury)) {
            return false;
        }
        return id != null && id.equals(((Jury) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jury{" +
            "id=" + getId() +
            ", nomPresident='" + getNomPresident() + "'" +
            ", nomRapporteur='" + getNomRapporteur() + "'" +
            ", nomProfAnglais='" + getNomProfAnglais() + "'" +
            ", numeroSalle='" + getNumeroSalle() + "'" +
            "}";
    }
}
