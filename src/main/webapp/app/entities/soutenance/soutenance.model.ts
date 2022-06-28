import dayjs from 'dayjs/esm';
import { IProjet } from 'app/entities/projet/projet.model';
import { IJury } from 'app/entities/jury/jury.model';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { Mention } from 'app/entities/enumerations/mention.model';

export interface ISoutenance {
  id?: number;
  mention?: Mention;
  note?: number;
  dateDuJour?: dayjs.Dayjs;
  remarque?: string | null;
  dateAjout?: dayjs.Dayjs | null;
  dateModification?: dayjs.Dayjs | null;
  noteAnglais?: number | null;
  noteFrancais?: number | null;
  noteTechnique?: number | null;
  rapportRendu?: boolean | null;
  projet?: IProjet | null;
  jury?: IJury | null;
  anneeAcademique?: IAnneeAcademique | null;
}

export class Soutenance implements ISoutenance {
  constructor(
    public id?: number,
    public mention?: Mention,
    public note?: number,
    public dateDuJour?: dayjs.Dayjs,
    public remarque?: string | null,
    public dateAjout?: dayjs.Dayjs | null,
    public dateModification?: dayjs.Dayjs | null,
    public noteAnglais?: number | null,
    public noteFrancais?: number | null,
    public noteTechnique?: number | null,
    public rapportRendu?: boolean | null,
    public projet?: IProjet | null,
    public jury?: IJury | null,
    public anneeAcademique?: IAnneeAcademique | null
  ) {}
}

export function getSoutenanceIdentifier(soutenance: ISoutenance): number | undefined {
  return soutenance.id;
}
