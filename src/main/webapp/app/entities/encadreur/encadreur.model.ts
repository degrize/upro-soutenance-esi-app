export interface IEncadreur {
  id?: number;
  nom?: string;
  prenoms?: string;
  contact?: string;
  email?: string | null;
}

export class Encadreur implements IEncadreur {
  constructor(public id?: number, public nom?: string, public prenoms?: string, public contact?: string, public email?: string | null) {}
}

export function getEncadreurIdentifier(encadreur: IEncadreur): number | undefined {
  return encadreur.id;
}
