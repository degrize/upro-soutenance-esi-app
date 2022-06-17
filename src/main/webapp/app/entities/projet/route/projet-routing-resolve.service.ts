import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjet, Projet } from '../projet.model';
import { ProjetService } from '../service/projet.service';

@Injectable({ providedIn: 'root' })
export class ProjetRoutingResolveService implements Resolve<IProjet> {
  constructor(protected service: ProjetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projet: HttpResponse<Projet>) => {
          if (projet.body) {
            return of(projet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Projet());
  }
}
