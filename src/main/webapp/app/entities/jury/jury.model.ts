import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { IGenie } from 'app/entities/genie/genie.model';
import { ISoutenance } from 'app/entities/soutenance/soutenance.model';

export interface IJury {
  id?: number;
  nomPresident?: string;
  nomRapporteur?: string;
  nomProfAnglais?: string;
  numeroSalle?: string | null;
  anneeAcademique?: IAnneeAcademique | null;
  genie?: IGenie | null;
  soutenances?: ISoutenance[] | null;
}

export class Jury implements IJury {
  constructor(
    public id?: number,
    public nomPresident?: string,
    public nomRapporteur?: string,
    public nomProfAnglais?: string,
    public numeroSalle?: string | null,
    public anneeAcademique?: IAnneeAcademique | null,
    public genie?: IGenie | null,
    public soutenances?: ISoutenance[] | null
  ) {}
}

export function getJuryIdentifier(jury: IJury): number | undefined {
  return jury.id;
}
