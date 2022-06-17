import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJury, Jury } from '../jury.model';
import { JuryService } from '../service/jury.service';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { AnneeAcademiqueService } from 'app/entities/annee-academique/service/annee-academique.service';
import { IGenie } from 'app/entities/genie/genie.model';
import { GenieService } from 'app/entities/genie/service/genie.service';

@Component({
  selector: 'jhi-jury-update',
  templateUrl: './jury-update.component.html',
})
export class JuryUpdateComponent implements OnInit {
  isSaving = false;

  anneeAcademiquesSharedCollection: IAnneeAcademique[] = [];
  geniesSharedCollection: IGenie[] = [];

  editForm = this.fb.group({
    id: [],
    nomPresident: [null, [Validators.required]],
    nomRapporteur: [null, [Validators.required]],
    nomProfAnglais: [null, [Validators.required]],
    numeroSalle: [],
    anneeAcademique: [],
    genie: [],
  });

  constructor(
    protected juryService: JuryService,
    protected anneeAcademiqueService: AnneeAcademiqueService,
    protected genieService: GenieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jury }) => {
      this.updateForm(jury);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jury = this.createFromForm();
    if (jury.id !== undefined) {
      this.subscribeToSaveResponse(this.juryService.update(jury));
    } else {
      this.subscribeToSaveResponse(this.juryService.create(jury));
    }
  }

  trackAnneeAcademiqueById(_index: number, item: IAnneeAcademique): number {
    return item.id!;
  }

  trackGenieById(_index: number, item: IGenie): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJury>>): void {
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

  protected updateForm(jury: IJury): void {
    this.editForm.patchValue({
      id: jury.id,
      nomPresident: jury.nomPresident,
      nomRapporteur: jury.nomRapporteur,
      nomProfAnglais: jury.nomProfAnglais,
      numeroSalle: jury.numeroSalle,
      anneeAcademique: jury.anneeAcademique,
      genie: jury.genie,
    });

    this.anneeAcademiquesSharedCollection = this.anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing(
      this.anneeAcademiquesSharedCollection,
      jury.anneeAcademique
    );
    this.geniesSharedCollection = this.genieService.addGenieToCollectionIfMissing(this.geniesSharedCollection, jury.genie);
  }

  protected loadRelationshipsOptions(): void {
    this.anneeAcademiqueService
      .query()
      .pipe(map((res: HttpResponse<IAnneeAcademique[]>) => res.body ?? []))
      .pipe(
        map((anneeAcademiques: IAnneeAcademique[]) =>
          this.anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing(anneeAcademiques, this.editForm.get('anneeAcademique')!.value)
        )
      )
      .subscribe((anneeAcademiques: IAnneeAcademique[]) => (this.anneeAcademiquesSharedCollection = anneeAcademiques));

    this.genieService
      .query()
      .pipe(map((res: HttpResponse<IGenie[]>) => res.body ?? []))
      .pipe(map((genies: IGenie[]) => this.genieService.addGenieToCollectionIfMissing(genies, this.editForm.get('genie')!.value)))
      .subscribe((genies: IGenie[]) => (this.geniesSharedCollection = genies));
  }

  protected createFromForm(): IJury {
    return {
      ...new Jury(),
      id: this.editForm.get(['id'])!.value,
      nomPresident: this.editForm.get(['nomPresident'])!.value,
      nomRapporteur: this.editForm.get(['nomRapporteur'])!.value,
      nomProfAnglais: this.editForm.get(['nomProfAnglais'])!.value,
      numeroSalle: this.editForm.get(['numeroSalle'])!.value,
      anneeAcademique: this.editForm.get(['anneeAcademique'])!.value,
      genie: this.editForm.get(['genie'])!.value,
    };
  }
}
