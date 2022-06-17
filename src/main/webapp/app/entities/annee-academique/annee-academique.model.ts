export interface IAnneeAcademique {
  id?: number;
  anneeScolaire?: string;
}

export class AnneeAcademique implements IAnneeAcademique {
  constructor(public id?: number, public anneeScolaire?: string) {}
}

export function getAnneeAcademiqueIdentifier(anneeAcademique: IAnneeAcademique): number | undefined {
  return anneeAcademique.id;
}
