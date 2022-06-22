import { Component, OnInit, HostListener, ViewChild, PipeTransform } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, Observable, startWith } from 'rxjs';
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

import { MdbTableDirective } from 'ng-uikit-pro-standard';

// ng-wizard

import { of } from 'rxjs';
import { NgWizardConfig, NgWizardService, StepChangedArgs, StepValidationArgs, STEP_STATE, THEME } from 'ng-wizard';
import { IEleve } from '../../eleve/eleve.model';
import { DecimalPipe } from '@angular/common';
import { EleveService } from '../../eleve/service/eleve.service';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from '../../../config/pagination.constants';
import { SituationMatrimoniale } from '../../enumerations/situation-matrimoniale.model';
import dayjs from 'dayjs/esm';
import { IEncadreur } from '../../encadreur/encadreur.model';
import { ISpecialite } from '../../specialite/specialite.model';

const ELEVES: IEleve[] = [
  {
    id: undefined,
    nom: undefined,
    prenoms: '',
    matricule: '',
    specialite: undefined,
    projet: undefined,
    sexe: undefined,
    situationMatrimoniale: undefined,
    dateParcoursDebut: null,
    dateParcoursFin: null,
    encadreur: null,
  },
];

@Component({
  selector: 'jhi-soutenance-update',
  templateUrl: './soutenance-update.component.html',
  styleUrls: ['./soutenance-update.component.scss'],
  providers: [DecimalPipe],
})
export class SoutenanceUpdateComponent implements OnInit {
  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;

  elements: any = [];
  headElements = ['ID', 'First', 'Last', 'Handle'];
  searchText = '';
  previous = '';

  choixEleve = 0;

  isSaving = false;
  mentionValues = Object.keys(Mention);
  stepOk = false;

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

  // ng-wizard
  isValidTypeBoolean = true;
  stepStates = {
    normal: STEP_STATE.normal,
    disabled: STEP_STATE.disabled,
    error: STEP_STATE.error,
    hidden: STEP_STATE.hidden,
  };

  config: NgWizardConfig = {
    selected: 0,
    theme: THEME.circles,
  };

  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  eleves?: IEleve[];
  eleves$?: Observable<IEleve[]>;

  filter = new FormControl('');

  constructor(
    protected soutenanceService: SoutenanceService,
    protected eleveService: EleveService,
    protected projetService: ProjetService,
    protected juryService: JuryService,
    protected anneeAcademiqueService: AnneeAcademiqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private ngWizardService: NgWizardService,
    pipe: DecimalPipe
  ) {
    this.eleves$ = this.filter.valueChanges.pipe(
      startWith(''),
      map(text => this.search(text, pipe))
    );
  }

  loadPage(page?: number): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    this.eleveService
      .findAll({
        page: null,
        size: null,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IEleve[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }

  search(text: string, pipe: PipeTransform): IEleve[] {
    if (this.eleves != null) {
      return this.eleves.filter(eleve => {
        const term = text.toLowerCase();
        if (eleve.nom != null && eleve.prenoms != null && eleve.matricule != null) {
          return (
            eleve.nom.toLowerCase().indexOf(term) > -1 ||
            eleve.prenoms.toLowerCase().indexOf(term) > -1 ||
            eleve.matricule.toLowerCase().indexOf(term) > -1 ||
            pipe.transform('')
          );
        }
      });
    }
    return ELEVES;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soutenance }) => {
      this.updateForm(soutenance);

      this.loadRelationshipsOptions();
    });

    this.loadPage(1);
  }

  // ng-wizard
  showPreviousStep(event?: Event): any {
    this.ngWizardService.previous();
  }

  showNextStep(event?: Event): any {
    this.ngWizardService.next();
  }

  resetWizard(event?: Event): void {
    this.ngWizardService.reset();
  }

  setTheme(theme: THEME): void {
    this.ngWizardService.theme(theme);
  }

  stepChanged(args: StepChangedArgs): void {
    console.log(args.step);
  }

  isValidFunctionReturnsBoolean(args: StepValidationArgs): boolean {
    return true;
  }

  isValidFunctionReturnsObservable(args: StepValidationArgs): Observable<boolean> {
    return of(true);
  }
  // end ng-wizard bloc code

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

  onChange(e: any): void {
    this.choixEleve = e.target.value;
    console.log(this.choixEleve);
  }

  displayRadioValue(): boolean {
    return this.choixEleve !== 0 ? true : false;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? DESC : ASC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
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

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber);
      }
    });
  }

  protected onSuccess(data: IEleve[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.eleves = data ?? [];
    this.ngbPaginationPage = this.page;
    console.log(data);
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
