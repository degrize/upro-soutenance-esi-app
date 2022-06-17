import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INoteEntrepriseRapport, NoteEntrepriseRapport } from '../note-entreprise-rapport.model';
import { NoteEntrepriseRapportService } from '../service/note-entreprise-rapport.service';

@Injectable({ providedIn: 'root' })
export class NoteEntrepriseRapportRoutingResolveService implements Resolve<INoteEntrepriseRapport> {
  constructor(protected service: NoteEntrepriseRapportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INoteEntrepriseRapport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((noteEntrepriseRapport: HttpResponse<NoteEntrepriseRapport>) => {
          if (noteEntrepriseRapport.body) {
            return of(noteEntrepriseRapport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NoteEntrepriseRapport());
  }
}
