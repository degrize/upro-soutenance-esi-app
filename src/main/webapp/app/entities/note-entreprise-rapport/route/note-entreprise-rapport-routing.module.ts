import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NoteEntrepriseRapportComponent } from '../list/note-entreprise-rapport.component';
import { NoteEntrepriseRapportDetailComponent } from '../detail/note-entreprise-rapport-detail.component';
import { NoteEntrepriseRapportUpdateComponent } from '../update/note-entreprise-rapport-update.component';
import { NoteEntrepriseRapportRoutingResolveService } from './note-entreprise-rapport-routing-resolve.service';

const noteEntrepriseRapportRoute: Routes = [
  {
    path: '',
    component: NoteEntrepriseRapportComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NoteEntrepriseRapportDetailComponent,
    resolve: {
      noteEntrepriseRapport: NoteEntrepriseRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NoteEntrepriseRapportUpdateComponent,
    resolve: {
      noteEntrepriseRapport: NoteEntrepriseRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NoteEntrepriseRapportUpdateComponent,
    resolve: {
      noteEntrepriseRapport: NoteEntrepriseRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(noteEntrepriseRapportRoute)],
  exports: [RouterModule],
})
export class NoteEntrepriseRapportRoutingModule {}
