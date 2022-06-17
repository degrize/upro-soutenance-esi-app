import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NoteEntrepriseRapportComponent } from './list/note-entreprise-rapport.component';
import { NoteEntrepriseRapportDetailComponent } from './detail/note-entreprise-rapport-detail.component';
import { NoteEntrepriseRapportUpdateComponent } from './update/note-entreprise-rapport-update.component';
import { NoteEntrepriseRapportDeleteDialogComponent } from './delete/note-entreprise-rapport-delete-dialog.component';
import { NoteEntrepriseRapportRoutingModule } from './route/note-entreprise-rapport-routing.module';

@NgModule({
  imports: [SharedModule, NoteEntrepriseRapportRoutingModule],
  declarations: [
    NoteEntrepriseRapportComponent,
    NoteEntrepriseRapportDetailComponent,
    NoteEntrepriseRapportUpdateComponent,
    NoteEntrepriseRapportDeleteDialogComponent,
  ],
  entryComponents: [NoteEntrepriseRapportDeleteDialogComponent],
})
export class NoteEntrepriseRapportModule {}
