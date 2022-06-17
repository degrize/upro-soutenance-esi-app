import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INoteEntrepriseRapport } from '../note-entreprise-rapport.model';
import { NoteEntrepriseRapportService } from '../service/note-entreprise-rapport.service';

@Component({
  templateUrl: './note-entreprise-rapport-delete-dialog.component.html',
})
export class NoteEntrepriseRapportDeleteDialogComponent {
  noteEntrepriseRapport?: INoteEntrepriseRapport;

  constructor(protected noteEntrepriseRapportService: NoteEntrepriseRapportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.noteEntrepriseRapportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
