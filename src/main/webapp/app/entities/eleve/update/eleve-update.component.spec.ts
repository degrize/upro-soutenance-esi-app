import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EleveService } from '../service/eleve.service';
import { IEleve, Eleve } from '../eleve.model';
import { IEncadreur } from 'app/entities/encadreur/encadreur.model';
import { EncadreurService } from 'app/entities/encadreur/service/encadreur.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { ISpecialite } from 'app/entities/specialite/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/service/specialite.service';

import { EleveUpdateComponent } from './eleve-update.component';

describe('Eleve Management Update Component', () => {
  let comp: EleveUpdateComponent;
  let fixture: ComponentFixture<EleveUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eleveService: EleveService;
  let encadreurService: EncadreurService;
  let projetService: ProjetService;
  let specialiteService: SpecialiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EleveUpdateComponent],
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
      .overrideTemplate(EleveUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EleveUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eleveService = TestBed.inject(EleveService);
    encadreurService = TestBed.inject(EncadreurService);
    projetService = TestBed.inject(ProjetService);
    specialiteService = TestBed.inject(SpecialiteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Encadreur query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const encadreur: IEncadreur = { id: 2352 };
      eleve.encadreur = encadreur;

      const encadreurCollection: IEncadreur[] = [{ id: 21725 }];
      jest.spyOn(encadreurService, 'query').mockReturnValue(of(new HttpResponse({ body: encadreurCollection })));
      const additionalEncadreurs = [encadreur];
      const expectedCollection: IEncadreur[] = [...additionalEncadreurs, ...encadreurCollection];
      jest.spyOn(encadreurService, 'addEncadreurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(encadreurService.query).toHaveBeenCalled();
      expect(encadreurService.addEncadreurToCollectionIfMissing).toHaveBeenCalledWith(encadreurCollection, ...additionalEncadreurs);
      expect(comp.encadreursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Projet query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const projet: IProjet = { id: 39548 };
      eleve.projet = projet;

      const projetCollection: IProjet[] = [{ id: 22119 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [projet];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(projetCollection, ...additionalProjets);
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Specialite query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const specialite: ISpecialite = { id: 20007 };
      eleve.specialite = specialite;

      const specialiteCollection: ISpecialite[] = [{ id: 10185 }];
      jest.spyOn(specialiteService, 'query').mockReturnValue(of(new HttpResponse({ body: specialiteCollection })));
      const additionalSpecialites = [specialite];
      const expectedCollection: ISpecialite[] = [...additionalSpecialites, ...specialiteCollection];
      jest.spyOn(specialiteService, 'addSpecialiteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(specialiteService.query).toHaveBeenCalled();
      expect(specialiteService.addSpecialiteToCollectionIfMissing).toHaveBeenCalledWith(specialiteCollection, ...additionalSpecialites);
      expect(comp.specialitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eleve: IEleve = { id: 456 };
      const encadreur: IEncadreur = { id: 97197 };
      eleve.encadreur = encadreur;
      const projet: IProjet = { id: 71137 };
      eleve.projet = projet;
      const specialite: ISpecialite = { id: 34826 };
      eleve.specialite = specialite;

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(eleve));
      expect(comp.encadreursSharedCollection).toContain(encadreur);
      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.specialitesSharedCollection).toContain(specialite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eleveService.update).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = new Eleve();
      jest.spyOn(eleveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(eleveService.create).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eleveService.update).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEncadreurById', () => {
      it('Should return tracked Encadreur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEncadreurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProjetById', () => {
      it('Should return tracked Projet primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProjetById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSpecialiteById', () => {
      it('Should return tracked Specialite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSpecialiteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
