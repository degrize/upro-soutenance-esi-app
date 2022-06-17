import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEncadreur, Encadreur } from '../encadreur.model';
import { EncadreurService } from '../service/encadreur.service';

@Component({
  selector: 'jhi-encadreur-update',
  templateUrl: './encadreur-update.component.html',
})
export class EncadreurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenoms: [null, [Validators.required]],
    contact: [null, [Validators.required]],
    email: [],
  });

  constructor(protected encadreurService: EncadreurService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encadreur }) => {
      this.updateForm(encadreur);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encadreur = this.createFromForm();
    if (encadreur.id !== undefined) {
      this.subscribeToSaveResponse(this.encadreurService.update(encadreur));
    } else {
      this.subscribeToSaveResponse(this.encadreurService.create(encadreur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncadreur>>): void {
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

  protected updateForm(encadreur: IEncadreur): void {
    this.editForm.patchValue({
      id: encadreur.id,
      nom: encadreur.nom,
      prenoms: encadreur.prenoms,
      contact: encadreur.contact,
      email: encadreur.email,
    });
  }

  protected createFromForm(): IEncadreur {
    return {
      ...new Encadreur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenoms: this.editForm.get(['prenoms'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }
}
