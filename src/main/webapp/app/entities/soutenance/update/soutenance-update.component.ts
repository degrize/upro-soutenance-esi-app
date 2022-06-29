import { Component, OnInit, PipeTransform, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
// ng-wizard
import { combineLatest, Observable, of, startWith } from 'rxjs';
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
import { NgWizardConfig, NgWizardService, STEP_STATE, StepChangedArgs, StepValidationArgs, THEME } from 'ng-wizard';
import { IEleve } from '../../eleve/eleve.model';
import { DecimalPipe } from '@angular/common';
import { EleveService } from '../../eleve/service/eleve.service';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from '../../../config/pagination.constants';
import { DataUtils, FileLoadError } from '../../../core/util/data-util.service';
import { EventManager, EventWithContent } from '../../../core/util/event-manager.service';
import { AlertError } from '../../../shared/alert/alert-error.model';

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
  cloturerSoutenance = false;

  isSaving = false;
  mentionValues = Object.keys(Mention);
  stepOk = false;

  projetsCollection: IProjet[] = [];
  juriesSharedCollection: IJury[] = [];
  anneeAcademiquesSharedCollection: IAnneeAcademique[] = [];

  editForm = this.fb.group({
    id: [],
    mention: [],
    note: [null, [Validators.required]],
    dateDuJour: [null, [Validators.required]],
    remarque: [],
    dateAjout: [],
    dateModification: [],
    projet: [],
    jury: [],
    anneeAcademique: [],
    rapportContentType: [],
    rapport: [],
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
    theme: THEME.default,
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
  validerSoutennace = false;
  isFormUpdate = false;

  filter = new FormControl('');

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
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

      this.validerSoutennace = soutenance?.note >= 12 ? true : false;
      this.isFormUpdate = soutenance?.id !== undefined ? true : false;

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
    if (soutenance.note !== undefined) {
      if (soutenance.note >= 10 && soutenance.note < 12) {
        soutenance.mention = Mention.PASSABLE;
      } else if (soutenance.note >= 12 && soutenance.note < 14) {
        soutenance.mention = Mention.ASSEZ_BIEN;
      } else if (soutenance.note >= 14 && soutenance.note < 16) {
        soutenance.mention = Mention.BIEN;
      } else if (soutenance.note >= 16 && soutenance.note < 18) {
        soutenance.mention = Mention.TRES_BIEN;
      } else if (soutenance.note >= 18 && soutenance.note < 20) {
        soutenance.mention = Mention.EXCELENTE;
      }
    }
    soutenance.rapportRendu = this.cloturerSoutenance;
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

  onChangeCloturerSoutenance(e: any): void {
    this.cloturerSoutenance = !this.cloturerSoutenance;
    console.log(this.cloturerSoutenance);
  }

  displayRadioValue(): boolean {
    return this.choixEleve !== 0 ? true : false;
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

    this.cloturerSoutenance = soutenance.rapportRendu ? true : false;

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
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
