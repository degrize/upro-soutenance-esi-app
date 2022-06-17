import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INoteEntrepriseRapport, NoteEntrepriseRapport } from '../note-entreprise-rapport.model';
import { NoteEntrepriseRapportService } from '../service/note-entreprise-rapport.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

@Component({
  selector: 'jhi-note-entreprise-rapport-update',
  templateUrl: './note-entreprise-rapport-update.component.html',
})
export class NoteEntrepriseRapportUpdateComponent implements OnInit {
  isSaving = false;

  entreprisesSharedCollection: IEntreprise[] = [];
  projetsSharedCollection: IProjet[] = [];

  editForm = this.fb.group({
    id: [],
    note: [null, [Validators.required]],
    observation: [],
    dateAjout: [],
    dateModification: [],
    entreprise: [],
    projet: [],
  });

  constructor(
    protected noteEntrepriseRapportService: NoteEntrepriseRapportService,
    protected entrepriseService: EntrepriseService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ noteEntrepriseRapport }) => {
      this.updateForm(noteEntrepriseRapport);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const noteEntrepriseRapport = this.createFromForm();
    if (noteEntrepriseRapport.id !== undefined) {
      this.subscribeToSaveResponse(this.noteEntrepriseRapportService.update(noteEntrepriseRapport));
    } else {
      this.subscribeToSaveResponse(this.noteEntrepriseRapportService.create(noteEntrepriseRapport));
    }
  }

  trackEntrepriseById(_index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackProjetById(_index: number, item: IProjet): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INoteEntrepriseRapport>>): void {
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

  protected updateForm(noteEntrepriseRapport: INoteEntrepriseRapport): void {
    this.editForm.patchValue({
      id: noteEntrepriseRapport.id,
      note: noteEntrepriseRapport.note,
      observation: noteEntrepriseRapport.observation,
      dateAjout: noteEntrepriseRapport.dateAjout,
      dateModification: noteEntrepriseRapport.dateModification,
      entreprise: noteEntrepriseRapport.entreprise,
      projet: noteEntrepriseRapport.projet,
    });

    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      noteEntrepriseRapport.entreprise
    );
    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing(
      this.projetsSharedCollection,
      noteEntrepriseRapport.projet
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing(projets, this.editForm.get('projet')!.value)))
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));
  }

  protected createFromForm(): INoteEntrepriseRapport {
    return {
      ...new NoteEntrepriseRapport(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      dateAjout: this.editForm.get(['dateAjout'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      projet: this.editForm.get(['projet'])!.value,
    };
  }
}
