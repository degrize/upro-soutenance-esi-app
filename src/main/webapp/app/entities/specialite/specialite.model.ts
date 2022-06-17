export interface ISpecialite {
  id?: number;
  nom?: string;
}

export class Specialite implements ISpecialite {
  constructor(public id?: number, public nom?: string) {}
}

export function getSpecialiteIdentifier(specialite: ISpecialite): number | undefined {
  return specialite.id;
}
