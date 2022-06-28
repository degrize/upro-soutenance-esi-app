package com.esi.uppro.service.dto;

import java.io.Serializable;

public class AdminStatisticsDTO implements Serializable {

    private int nbreRapportRendus;
    private int nbreEleveSoutenus;
    private int nbreEleveAjourne;
    private int nbreMentionAssezBien;
    private int nbreMentionBien;
    private int nbreMentionPassable;
    private int nbreMentionTresBien;

    private int nbreSoutenuJuillet;
    private int nbreSoutenuOctobre;
    private int nbreSoutenuFevrier;
    private int nbreSoutenuMars;

    private String anneeAcademique;

    public int getNbreRapportRendus() {
        return nbreRapportRendus;
    }

    public void setNbreRapportRendus(int nbreRapportRendus) {
        this.nbreRapportRendus = nbreRapportRendus;
    }

    public int getNbreEleveSoutenus() {
        return nbreEleveSoutenus;
    }

    public void setNbreEleveSoutenus(int nbreEleveSoutenus) {
        this.nbreEleveSoutenus = nbreEleveSoutenus;
    }

    public int getNbreEleveAjourne() {
        return nbreEleveAjourne;
    }

    public void setNbreEleveAjourne(int nbreEleveAjourne) {
        this.nbreEleveAjourne = nbreEleveAjourne;
    }

    public int getNbreMentionAssezBien() {
        return nbreMentionAssezBien;
    }

    public void setNbreMentionAssezBien(int nbreMentionAssezBien) {
        this.nbreMentionAssezBien = nbreMentionAssezBien;
    }

    public int getNbreMentionBien() {
        return nbreMentionBien;
    }

    public void setNbreMentionBien(int nbreMentionBien) {
        this.nbreMentionBien = nbreMentionBien;
    }

    public int getNbreMentionPassable() {
        return nbreMentionPassable;
    }

    public void setNbreMentionPassable(int nbreMentionPassable) {
        this.nbreMentionPassable = nbreMentionPassable;
    }

    public int getNbreMentionTresBien() {
        return nbreMentionTresBien;
    }

    public void setNbreMentionTresBien(int nbreMentionTresBien) {
        this.nbreMentionTresBien = nbreMentionTresBien;
    }

    public int getNbreSoutenuJuillet() {
        return nbreSoutenuJuillet;
    }

    public void setNbreSoutenuJuillet(int nbreSoutenuJuillet) {
        this.nbreSoutenuJuillet = nbreSoutenuJuillet;
    }

    public int getNbreSoutenuOctobre() {
        return nbreSoutenuOctobre;
    }

    public void setNbreSoutenuOctobre(int nbreSoutenuOctobre) {
        this.nbreSoutenuOctobre = nbreSoutenuOctobre;
    }

    public int getNbreSoutenuFevrier() {
        return nbreSoutenuFevrier;
    }

    public void setNbreSoutenuFevrier(int nbreSoutenuFevrier) {
        this.nbreSoutenuFevrier = nbreSoutenuFevrier;
    }

    public int getNbreSoutenuMars() {
        return nbreSoutenuMars;
    }

    public void setNbreSoutenuMars(int nbreSoutenuMars) {
        this.nbreSoutenuMars = nbreSoutenuMars;
    }

    public String getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(String anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    @Override
    public String toString() {
        return (
            "AdminStatisticsDTO{" +
            "nbreRapportRendus=" +
            nbreRapportRendus +
            ", nbreEleveSoutenus=" +
            nbreEleveSoutenus +
            ", nbreEleveAjourne=" +
            nbreEleveAjourne +
            ", nbreMentionAssezBien=" +
            nbreMentionAssezBien +
            ", nbreMentionBien=" +
            nbreMentionBien +
            ", nbreMentionPassable=" +
            nbreMentionPassable +
            ", nbreMentionTresBien=" +
            nbreMentionTresBien +
            ", nbreSoutenuJuillet=" +
            nbreSoutenuJuillet +
            ", nbreSoutenuOctobre=" +
            nbreSoutenuOctobre +
            ", nbreSoutenuFevrier=" +
            nbreSoutenuFevrier +
            ", nbreSoutenuMars=" +
            nbreSoutenuMars +
            ", anneeAcademique='" +
            anneeAcademique +
            '\'' +
            '}'
        );
    }
}
