import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGenie, Genie } from '../genie.model';
import { GenieService } from '../service/genie.service';

@Component({
  selector: 'jhi-genie-update',
  templateUrl: './genie-update.component.html',
})
export class GenieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    nomDirecteur: [],
    contactDirecteur: [],
  });

  constructor(protected genieService: GenieService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genie }) => {
      this.updateForm(genie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const genie = this.createFromForm();
    if (genie.id !== undefined) {
      this.subscribeToSaveResponse(this.genieService.update(genie));
    } else {
      this.subscribeToSaveResponse(this.genieService.create(genie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenie>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(genie: IGenie): void {
    this.editForm.patchValue({
      id: genie.id,
      nom: genie.nom,
      nomDirecteur: genie.nomDirecteur,
      contactDirecteur: genie.contactDirecteur,
    });
  }

  protected createFromForm(): IGenie {
    return {
      ...new Genie(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      nomDirecteur: this.editForm.get(['nomDirecteur'])!.value,
      contactDirecteur: this.editForm.get(['contactDirecteur'])!.value,
    };
  }
}
