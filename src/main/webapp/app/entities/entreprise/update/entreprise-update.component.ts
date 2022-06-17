import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEntreprise, Entreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';

@Component({
  selector: 'jhi-entreprise-update',
  templateUrl: './entreprise-update.component.html',
})
export class EntrepriseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    codeENtreprise: [null, [Validators.required]],
    secteurActivite: [],
    adresse: [],
    nomDirecteur: [],
    contactDirecteur: [],
    emailDirecteur: [],
    nomMaitreStage: [],
    contactMaitreStage: [],
    emailMaitreStage: [],
  });

  constructor(protected entrepriseService: EntrepriseService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entreprise }) => {
      this.updateForm(entreprise);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entreprise = this.createFromForm();
    if (entreprise.id !== undefined) {
      this.subscribeToSaveResponse(this.entrepriseService.update(entreprise));
    } else {
      this.subscribeToSaveResponse(this.entrepriseService.create(entreprise));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntreprise>>): void {
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

  protected updateForm(entreprise: IEntreprise): void {
    this.editForm.patchValue({
      id: entreprise.id,
      nom: entreprise.nom,
      codeENtreprise: entreprise.codeENtreprise,
      secteurActivite: entreprise.secteurActivite,
      adresse: entreprise.adresse,
      nomDirecteur: entreprise.nomDirecteur,
      contactDirecteur: entreprise.contactDirecteur,
      emailDirecteur: entreprise.emailDirecteur,
      nomMaitreStage: entreprise.nomMaitreStage,
      contactMaitreStage: entreprise.contactMaitreStage,
      emailMaitreStage: entreprise.emailMaitreStage,
    });
  }

  protected createFromForm(): IEntreprise {
    return {
      ...new Entreprise(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      codeENtreprise: this.editForm.get(['codeENtreprise'])!.value,
      secteurActivite: this.editForm.get(['secteurActivite'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      nomDirecteur: this.editForm.get(['nomDirecteur'])!.value,
      contactDirecteur: this.editForm.get(['contactDirecteur'])!.value,
      emailDirecteur: this.editForm.get(['emailDirecteur'])!.value,
      nomMaitreStage: this.editForm.get(['nomMaitreStage'])!.value,
      contactMaitreStage: this.editForm.get(['contactMaitreStage'])!.value,
      emailMaitreStage: this.editForm.get(['emailMaitreStage'])!.value,
    };
  }
}
