export interface IAdminStatistics {
  nbreRapportRendus?: number;
  nbreEleveSoutenus?: number;
  nbreEleveAjourne?: number;
  nbreMentionBien?: number;
  nbreMentionAssezBien?: number;
  nbreMentionPassable?: number;
  nbreMentionTresBien?: number;
  nbreSoutenuJuillet?: number;
  nbreSoutenuOctobre?: number;
  nbreSoutenuFevrier?: number;
  nbreSoutenuMars?: number;
  anneeAcademique?: string;
}

export class AdminStatistics implements IAdminStatistics {
  constructor(
    public nbreRapportRendus?: number,
    public nbreEleveSoutenus?: number,
    public nbreEleveAjourne?: number,
    public nbreMentionBien?: number,
    public nbreMentionAssezBien?: number,
    public nbreMentionPassable?: number,
    public nbreMentionTresBien?: number,
    public nbreSoutenuJuillet?: number,
    public nbreSoutenuOctobre?: number,
    public nbreSoutenuFevrier?: number,
    public nbreSoutenuMars?: number,
    public anneeAcademique?: string
  ) {}
}
