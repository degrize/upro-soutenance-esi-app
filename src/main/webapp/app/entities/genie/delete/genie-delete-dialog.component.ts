import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGenie } from '../genie.model';
import { GenieService } from '../service/genie.service';

@Component({
  templateUrl: './genie-delete-dialog.component.html',
})
export class GenieDeleteDialogComponent {
  genie?: IGenie;

  constructor(protected genieService: GenieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.genieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
