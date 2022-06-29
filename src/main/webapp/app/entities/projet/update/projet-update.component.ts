import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProjet, Projet } from '../projet.model';
import { ProjetService } from '../service/projet.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { AnneeAcademiqueService } from 'app/entities/annee-academique/service/annee-academique.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';

@Component({
  selector: 'jhi-projet-update',
  templateUrl: './projet-update.component.html',
})
export class ProjetUpdateComponent implements OnInit {
  isSaving = false;

  anneeAcademiquesSharedCollection: IAnneeAcademique[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];

  editForm = this.fb.group({
    id: [],
    theme: [null, [Validators.required]],
    rapport: [],
    rapportContentType: [],
    cout: [],
    dateAjout: [],
    dateModification: [],
    anneeAcademique: [],
    entreprises: [],
    valide: [false],
    dateSoutenance: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projetService: ProjetService,
    protected anneeAcademiqueService: AnneeAcademiqueService,
    protected entrepriseService: EntrepriseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.updateForm(projet);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('uproSoutenanceEsiApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projet = this.createFromForm();
    if (projet.id !== undefined) {
      this.subscribeToSaveResponse(this.projetService.update(projet));
    } else {
      this.subscribeToSaveResponse(this.projetService.create(projet));
    }
  }

  trackAnneeAcademiqueById(_index: number, item: IAnneeAcademique): number {
    return item.id!;
  }

  trackEntrepriseById(_index: number, item: IEntreprise): number {
    return item.id!;
  }

  getSelectedEntreprise(option: IEntreprise, selectedVals?: IEntreprise[]): IEntreprise {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>): void {
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

  protected updateForm(projet: IProjet): void {
    this.editForm.patchValue({
      id: projet.id,
      theme: projet.theme,
      rapport: projet.rapport,
      rapportContentType: projet.rapportContentType,
      cout: projet.cout,
      dateAjout: projet.dateAjout,
      dateModification: projet.dateModification,
      anneeAcademique: projet.anneeAcademique,
      entreprises: projet.entreprises,
    });

    this.anneeAcademiquesSharedCollection = this.anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing(
      this.anneeAcademiquesSharedCollection,
      projet.anneeAcademique
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      ...(projet.entreprises ?? [])
    );
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

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, ...(this.editForm.get('entreprises')!.value ?? []))
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));
  }

  protected createFromForm(): IProjet {
    return {
      ...new Projet(),
      id: this.editForm.get(['id'])!.value,
      theme: this.editForm.get(['theme'])!.value,
      rapportContentType: this.editForm.get(['rapportContentType'])!.value,
      rapport: this.editForm.get(['rapport'])!.value,
      cout: this.editForm.get(['cout'])!.value,
      dateAjout: this.editForm.get(['dateAjout'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      anneeAcademique: this.editForm.get(['anneeAcademique'])!.value,
      entreprises: this.editForm.get(['entreprises'])!.value,
    };
  }
}
