export interface IGenie {
  id?: number;
  nom?: string;
  nomDirecteur?: string | null;
  contactDirecteur?: string | null;
}

export class Genie implements IGenie {
  constructor(public id?: number, public nom?: string, public nomDirecteur?: string | null, public contactDirecteur?: string | null) {}
}

export function getGenieIdentifier(genie: IGenie): number | undefined {
  return genie.id;
}
