import dayjs from 'dayjs/esm';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IProjet } from 'app/entities/projet/projet.model';

export interface INoteEntrepriseRapport {
  id?: number;
  note?: number;
  observation?: string | null;
  dateAjout?: dayjs.Dayjs | null;
  dateModification?: dayjs.Dayjs | null;
  entreprise?: IEntreprise | null;
  projet?: IProjet | null;
}

export class NoteEntrepriseRapport implements INoteEntrepriseRapport {
  constructor(
    public id?: number,
    public note?: number,
    public observation?: string | null,
    public dateAjout?: dayjs.Dayjs | null,
    public dateModification?: dayjs.Dayjs | null,
    public entreprise?: IEntreprise | null,
    public projet?: IProjet | null
  ) {}
}

export function getNoteEntrepriseRapportIdentifier(noteEntrepriseRapport: INoteEntrepriseRapport): number | undefined {
  return noteEntrepriseRapport.id;
}
