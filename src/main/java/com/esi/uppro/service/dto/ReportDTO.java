package com.esi.uppro.service.dto;

public class ReportDTO {

    private String dateCourante;

    private int totaEleveSoutenus;

    private int totalEleveDepoRapport;

    private int totalEleveValideSoutenance;

    private int totalEleveAjournee;

    private int totalEleveInscrit;

    public ReportDTO() {}

    public String getDateCourante() {
        return dateCourante;
    }

    public void setDateCourante(String dateCourante) {
        this.dateCourante = dateCourante;
    }

    public int getTotaEleveSoutenus() {
        return totaEleveSoutenus;
    }

    public void setTotaEleveSoutenus(int totaEleveSoutenus) {
        this.totaEleveSoutenus = totaEleveSoutenus;
    }

    public int getTotalEleveDepoRapport() {
        return totalEleveDepoRapport;
    }

    public void setTotalEleveDepoRapport(int totalEleveDepoRapport) {
        this.totalEleveDepoRapport = totalEleveDepoRapport;
    }

    public int getTotalEleveValideSoutenance() {
        return totalEleveValideSoutenance;
    }

    public void setTotalEleveValideSoutenance(int totalEleveValideSoutenance) {
        this.totalEleveValideSoutenance = totalEleveValideSoutenance;
    }

    public int getTotalEleveAjournee() {
        return totalEleveAjournee;
    }

    public void setTotalEleveAjournee(int totalEleveAjournee) {
        this.totalEleveAjournee = totalEleveAjournee;
    }

    public int getTotalEleveInscrit() {
        return totalEleveInscrit;
    }

    public void setTotalEleveInscrit(int totalEleveInscrit) {
        this.totalEleveInscrit = totalEleveInscrit;
    }

    @Override
    public String toString() {
        return (
            "ReportDTO{" +
            "dateCourante='" +
            dateCourante +
            '\'' +
            ", totaEleveSoutenus=" +
            totaEleveSoutenus +
            ", totalEleveDepoRapport=" +
            totalEleveDepoRapport +
            ", totalEleveValideSoutenance=" +
            totalEleveValideSoutenance +
            ", totalEleveAjournee=" +
            totalEleveAjournee +
            ", totalEleveInscrit=" +
            totalEleveInscrit +
            '}'
        );
    }
}
