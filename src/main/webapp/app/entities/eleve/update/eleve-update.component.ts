import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEleve, Eleve } from '../eleve.model';
import { EleveService } from '../service/eleve.service';
import { IEncadreur } from 'app/entities/encadreur/encadreur.model';
import { EncadreurService } from 'app/entities/encadreur/service/encadreur.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { ISpecialite } from 'app/entities/specialite/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/service/specialite.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';
import { Account } from '../../../core/auth/account.model';
import { AccountService } from '../../../core/auth/account.service';

@Component({
  selector: 'jhi-eleve-update',
  templateUrl: './eleve-update.component.html',
})
export class EleveUpdateComponent implements OnInit {
  account$?: Observable<Account | null>;
  isSaving = false;
  sexeValues = Object.keys(Sexe);
  situationMatrimonialeValues = Object.keys(SituationMatrimoniale);

  encadreursSharedCollection: IEncadreur[] = [];
  projetsSharedCollection: IProjet[] = [];
  specialitesSharedCollection: ISpecialite[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    prenoms: [null, [Validators.required]],
    sexe: [],
    situationMatrimoniale: [null, [Validators.required]],
    dateParcoursDebut: [],
    dateParcoursFin: [],
    encadreur: [],
    projet: [],
    specialite: [],
  });

  constructor(
    protected eleveService: EleveService,
    private accountService: AccountService,
    protected encadreurService: EncadreurService,
    protected projetService: ProjetService,
    protected specialiteService: SpecialiteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eleve }) => {
      this.updateForm(eleve);

      this.loadRelationshipsOptions();
    });

    this.account$ = this.accountService.identity();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eleve = this.createFromForm();
    if (eleve.id !== undefined) {
      this.subscribeToSaveResponse(this.eleveService.update(eleve));
    } else {
      this.subscribeToSaveResponse(this.eleveService.create(eleve));
    }
  }

  trackEncadreurById(_index: number, item: IEncadreur): number {
    return item.id!;
  }

  trackProjetById(_index: number, item: IProjet): number {
    return item.id!;
  }

  trackSpecialiteById(_index: number, item: ISpecialite): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEleve>>): void {
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

  protected updateForm(eleve: IEleve): void {
    this.editForm.patchValue({
      id: eleve.id,
      matricule: eleve.matricule,
      nom: eleve.nom,
      prenoms: eleve.prenoms,
      sexe: eleve.sexe,
      situationMatrimoniale: eleve.situationMatrimoniale,
      dateParcoursDebut: eleve.dateParcoursDebut,
      dateParcoursFin: eleve.dateParcoursFin,
      encadreur: eleve.encadreur,
      projet: eleve.projet,
      specialite: eleve.specialite,
    });

    this.encadreursSharedCollection = this.encadreurService.addEncadreurToCollectionIfMissing(
      this.encadreursSharedCollection,
      eleve.encadreur
    );
    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing(this.projetsSharedCollection, eleve.projet);
    this.specialitesSharedCollection = this.specialiteService.addSpecialiteToCollectionIfMissing(
      this.specialitesSharedCollection,
      eleve.specialite
    );
  }

  protected loadRelationshipsOptions(): void {
    this.encadreurService
      .query()
      .pipe(map((res: HttpResponse<IEncadreur[]>) => res.body ?? []))
      .pipe(
        map((encadreurs: IEncadreur[]) =>
          this.encadreurService.addEncadreurToCollectionIfMissing(encadreurs, this.editForm.get('encadreur')!.value)
        )
      )
      .subscribe((encadreurs: IEncadreur[]) => (this.encadreursSharedCollection = encadreurs));

    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing(projets, this.editForm.get('projet')!.value)))
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));

    this.specialiteService
      .query()
      .pipe(map((res: HttpResponse<ISpecialite[]>) => res.body ?? []))
      .pipe(
        map((specialites: ISpecialite[]) =>
          this.specialiteService.addSpecialiteToCollectionIfMissing(specialites, this.editForm.get('specialite')!.value)
        )
      )
      .subscribe((specialites: ISpecialite[]) => (this.specialitesSharedCollection = specialites));
  }

  protected createFromForm(): IEleve {
    return {
      ...new Eleve(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenoms: this.editForm.get(['prenoms'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      situationMatrimoniale: this.editForm.get(['situationMatrimoniale'])!.value,
      dateParcoursDebut: this.editForm.get(['dateParcoursDebut'])!.value,
      dateParcoursFin: this.editForm.get(['dateParcoursFin'])!.value,
      encadreur: this.editForm.get(['encadreur'])!.value,
      projet: this.editForm.get(['projet'])!.value,
      specialite: this.editForm.get(['specialite'])!.value,
    };
  }
}
