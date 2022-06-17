import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SoutenanceService } from '../service/soutenance.service';
import { ISoutenance, Soutenance } from '../soutenance.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IJury } from 'app/entities/jury/jury.model';
import { JuryService } from 'app/entities/jury/service/jury.service';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { AnneeAcademiqueService } from 'app/entities/annee-academique/service/annee-academique.service';

import { SoutenanceUpdateComponent } from './soutenance-update.component';

describe('Soutenance Management Update Component', () => {
  let comp: SoutenanceUpdateComponent;
  let fixture: ComponentFixture<SoutenanceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soutenanceService: SoutenanceService;
  let projetService: ProjetService;
  let juryService: JuryService;
  let anneeAcademiqueService: AnneeAcademiqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SoutenanceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SoutenanceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoutenanceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soutenanceService = TestBed.inject(SoutenanceService);
    projetService = TestBed.inject(ProjetService);
    juryService = TestBed.inject(JuryService);
    anneeAcademiqueService = TestBed.inject(AnneeAcademiqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call projet query and add missing value', () => {
      const soutenance: ISoutenance = { id: 456 };
      const projet: IProjet = { id: 42037 };
      soutenance.projet = projet;

      const projetCollection: IProjet[] = [{ id: 16402 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const expectedCollection: IProjet[] = [projet, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(projetCollection, projet);
      expect(comp.projetsCollection).toEqual(expectedCollection);
    });

    it('Should call Jury query and add missing value', () => {
      const soutenance: ISoutenance = { id: 456 };
      const jury: IJury = { id: 20175 };
      soutenance.jury = jury;

      const juryCollection: IJury[] = [{ id: 77738 }];
      jest.spyOn(juryService, 'query').mockReturnValue(of(new HttpResponse({ body: juryCollection })));
      const additionalJuries = [jury];
      const expectedCollection: IJury[] = [...additionalJuries, ...juryCollection];
      jest.spyOn(juryService, 'addJuryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      expect(juryService.query).toHaveBeenCalled();
      expect(juryService.addJuryToCollectionIfMissing).toHaveBeenCalledWith(juryCollection, ...additionalJuries);
      expect(comp.juriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AnneeAcademique query and add missing value', () => {
      const soutenance: ISoutenance = { id: 456 };
      const anneeAcademique: IAnneeAcademique = { id: 88921 };
      soutenance.anneeAcademique = anneeAcademique;

      const anneeAcademiqueCollection: IAnneeAcademique[] = [{ id: 44200 }];
      jest.spyOn(anneeAcademiqueService, 'query').mockReturnValue(of(new HttpResponse({ body: anneeAcademiqueCollection })));
      const additionalAnneeAcademiques = [anneeAcademique];
      const expectedCollection: IAnneeAcademique[] = [...additionalAnneeAcademiques, ...anneeAcademiqueCollection];
      jest.spyOn(anneeAcademiqueService, 'addAnneeAcademiqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      expect(anneeAcademiqueService.query).toHaveBeenCalled();
      expect(anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing).toHaveBeenCalledWith(
        anneeAcademiqueCollection,
        ...additionalAnneeAcademiques
      );
      expect(comp.anneeAcademiquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soutenance: ISoutenance = { id: 456 };
      const projet: IProjet = { id: 48671 };
      soutenance.projet = projet;
      const jury: IJury = { id: 14766 };
      soutenance.jury = jury;
      const anneeAcademique: IAnneeAcademique = { id: 77868 };
      soutenance.anneeAcademique = anneeAcademique;

      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soutenance));
      expect(comp.projetsCollection).toContain(projet);
      expect(comp.juriesSharedCollection).toContain(jury);
      expect(comp.anneeAcademiquesSharedCollection).toContain(anneeAcademique);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Soutenance>>();
      const soutenance = { id: 123 };
      jest.spyOn(soutenanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soutenance }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soutenanceService.update).toHaveBeenCalledWith(soutenance);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Soutenance>>();
      const soutenance = new Soutenance();
      jest.spyOn(soutenanceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soutenance }));
      saveSubject.complete();

      // THEN
      expect(soutenanceService.create).toHaveBeenCalledWith(soutenance);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Soutenance>>();
      const soutenance = { id: 123 };
      jest.spyOn(soutenanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soutenance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soutenanceService.update).toHaveBeenCalledWith(soutenance);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProjetById', () => {
      it('Should return tracked Projet primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProjetById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackJuryById', () => {
      it('Should return tracked Jury primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackJuryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAnneeAcademiqueById', () => {
      it('Should return tracked AnneeAcademique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAnneeAcademiqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
