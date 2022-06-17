import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISoutenance, Soutenance } from '../soutenance.model';
import { SoutenanceService } from '../service/soutenance.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IJury } from 'app/entities/jury/jury.model';
import { JuryService } from 'app/entities/jury/service/jury.service';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { AnneeAcademiqueService } from 'app/entities/annee-academique/service/annee-academique.service';
import { Mention } from 'app/entities/enumerations/mention.model';

@Component({
  selector: 'jhi-soutenance-update',
  templateUrl: './soutenance-update.component.html',
})
export class SoutenanceUpdateComponent implements OnInit {
  isSaving = false;
  mentionValues = Object.keys(Mention);

  projetsCollection: IProjet[] = [];
  juriesSharedCollection: IJury[] = [];
  anneeAcademiquesSharedCollection: IAnneeAcademique[] = [];

  editForm = this.fb.group({
    id: [],
    mention: [null, [Validators.required]],
    note: [null, [Validators.required]],
    dateDuJour: [null, [Validators.required]],
    remarque: [],
    dateAjout: [],
    dateModification: [],
    projet: [],
    jury: [],
    anneeAcademique: [],
  });

  constructor(
    protected soutenanceService: SoutenanceService,
    protected projetService: ProjetService,
    protected juryService: JuryService,
    protected anneeAcademiqueService: AnneeAcademiqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soutenance }) => {
      this.updateForm(soutenance);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soutenance = this.createFromForm();
    if (soutenance.id !== undefined) {
      this.subscribeToSaveResponse(this.soutenanceService.update(soutenance));
    } else {
      this.subscribeToSaveResponse(this.soutenanceService.create(soutenance));
    }
  }

  trackProjetById(_index: number, item: IProjet): number {
    return item.id!;
  }

  trackJuryById(_index: number, item: IJury): number {
    return item.id!;
  }

  trackAnneeAcademiqueById(_index: number, item: IAnneeAcademique): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoutenance>>): void {
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

  protected updateForm(soutenance: ISoutenance): void {
    this.editForm.patchValue({
      id: soutenance.id,
      mention: soutenance.mention,
      note: soutenance.note,
      dateDuJour: soutenance.dateDuJour,
      remarque: soutenance.remarque,
      dateAjout: soutenance.dateAjout,
      dateModification: soutenance.dateModification,
      projet: soutenance.projet,
      jury: soutenance.jury,
      anneeAcademique: soutenance.anneeAcademique,
    });

    this.projetsCollection = this.projetService.addProjetToCollectionIfMissing(this.projetsCollection, soutenance.projet);
    this.juriesSharedCollection = this.juryService.addJuryToCollectionIfMissing(this.juriesSharedCollection, soutenance.jury);
    this.anneeAcademiquesSharedCollection = this.anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing(
      this.anneeAcademiquesSharedCollection,
      soutenance.anneeAcademique
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projetService
      .query({ filter: 'soutenance-is-null' })
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing(projets, this.editForm.get('projet')!.value)))
      .subscribe((projets: IProjet[]) => (this.projetsCollection = projets));

    this.juryService
      .query()
      .pipe(map((res: HttpResponse<IJury[]>) => res.body ?? []))
      .pipe(map((juries: IJury[]) => this.juryService.addJuryToCollectionIfMissing(juries, this.editForm.get('jury')!.value)))
      .subscribe((juries: IJury[]) => (this.juriesSharedCollection = juries));

    this.anneeAcademiqueService
      .query()
      .pipe(map((res: HttpResponse<IAnneeAcademique[]>) => res.body ?? []))
      .pipe(
        map((anneeAcademiques: IAnneeAcademique[]) =>
          this.anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing(anneeAcademiques, this.editForm.get('anneeAcademique')!.value)
        )
      )
      .subscribe((anneeAcademiques: IAnneeAcademique[]) => (this.anneeAcademiquesSharedCollection = anneeAcademiques));
  }

  protected createFromForm(): ISoutenance {
    return {
      ...new Soutenance(),
      id: this.editForm.get(['id'])!.value,
      mention: this.editForm.get(['mention'])!.value,
      note: this.editForm.get(['note'])!.value,
      dateDuJour: this.editForm.get(['dateDuJour'])!.value,
      remarque: this.editForm.get(['remarque'])!.value,
      dateAjout: this.editForm.get(['dateAjout'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      projet: this.editForm.get(['projet'])!.value,
      jury: this.editForm.get(['jury'])!.value,
      anneeAcademique: this.editForm.get(['anneeAcademique'])!.value,
    };
  }
}
