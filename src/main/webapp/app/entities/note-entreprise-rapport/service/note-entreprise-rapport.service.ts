import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INoteEntrepriseRapport, getNoteEntrepriseRapportIdentifier } from '../note-entreprise-rapport.model';

export type EntityResponseType = HttpResponse<INoteEntrepriseRapport>;
export type EntityArrayResponseType = HttpResponse<INoteEntrepriseRapport[]>;

@Injectable({ providedIn: 'root' })
export class NoteEntrepriseRapportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/note-entreprise-rapports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(noteEntrepriseRapport: INoteEntrepriseRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(noteEntrepriseRapport);
    return this.http
      .post<INoteEntrepriseRapport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(noteEntrepriseRapport: INoteEntrepriseRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(noteEntrepriseRapport);
    return this.http
      .put<INoteEntrepriseRapport>(`${this.resourceUrl}/${getNoteEntrepriseRapportIdentifier(noteEntrepriseRapport) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(noteEntrepriseRapport: INoteEntrepriseRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(noteEntrepriseRapport);
    return this.http
      .patch<INoteEntrepriseRapport>(`${this.resourceUrl}/${getNoteEntrepriseRapportIdentifier(noteEntrepriseRapport) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INoteEntrepriseRapport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INoteEntrepriseRapport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNoteEntrepriseRapportToCollectionIfMissing(
    noteEntrepriseRapportCollection: INoteEntrepriseRapport[],
    ...noteEntrepriseRapportsToCheck: (INoteEntrepriseRapport | null | undefined)[]
  ): INoteEntrepriseRapport[] {
    const noteEntrepriseRapports: INoteEntrepriseRapport[] = noteEntrepriseRapportsToCheck.filter(isPresent);
    if (noteEntrepriseRapports.length > 0) {
      const noteEntrepriseRapportCollectionIdentifiers = noteEntrepriseRapportCollection.map(
        noteEntrepriseRapportItem => getNoteEntrepriseRapportIdentifier(noteEntrepriseRapportItem)!
      );
      const noteEntrepriseRapportsToAdd = noteEntrepriseRapports.filter(noteEntrepriseRapportItem => {
        const noteEntrepriseRapportIdentifier = getNoteEntrepriseRapportIdentifier(noteEntrepriseRapportItem);
        if (
          noteEntrepriseRapportIdentifier == null ||
          noteEntrepriseRapportCollectionIdentifiers.includes(noteEntrepriseRapportIdentifier)
        ) {
          return false;
        }
        noteEntrepriseRapportCollectionIdentifiers.push(noteEntrepriseRapportIdentifier);
        return true;
      });
      return [...noteEntrepriseRapportsToAdd, ...noteEntrepriseRapportCollection];
    }
    return noteEntrepriseRapportCollection;
  }

  protected convertDateFromClient(noteEntrepriseRapport: INoteEntrepriseRapport): INoteEntrepriseRapport {
    return Object.assign({}, noteEntrepriseRapport, {
      dateAjout: noteEntrepriseRapport.dateAjout?.isValid() ? noteEntrepriseRapport.dateAjout.format(DATE_FORMAT) : undefined,
      dateModification: noteEntrepriseRapport.dateModification?.isValid()
        ? noteEntrepriseRapport.dateModification.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAjout = res.body.dateAjout ? dayjs(res.body.dateAjout) : undefined;
      res.body.dateModification = res.body.dateModification ? dayjs(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((noteEntrepriseRapport: INoteEntrepriseRapport) => {
        noteEntrepriseRapport.dateAjout = noteEntrepriseRapport.dateAjout ? dayjs(noteEntrepriseRapport.dateAjout) : undefined;
        noteEntrepriseRapport.dateModification = noteEntrepriseRapport.dateModification
          ? dayjs(noteEntrepriseRapport.dateModification)
          : undefined;
      });
    }
    return res;
  }
}
