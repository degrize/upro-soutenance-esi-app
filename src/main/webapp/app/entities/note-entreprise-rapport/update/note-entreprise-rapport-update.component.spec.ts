import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NoteEntrepriseRapportService } from '../service/note-entreprise-rapport.service';
import { INoteEntrepriseRapport, NoteEntrepriseRapport } from '../note-entreprise-rapport.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

import { NoteEntrepriseRapportUpdateComponent } from './note-entreprise-rapport-update.component';

describe('NoteEntrepriseRapport Management Update Component', () => {
  let comp: NoteEntrepriseRapportUpdateComponent;
  let fixture: ComponentFixture<NoteEntrepriseRapportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let noteEntrepriseRapportService: NoteEntrepriseRapportService;
  let entrepriseService: EntrepriseService;
  let projetService: ProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NoteEntrepriseRapportUpdateComponent],
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
      .overrideTemplate(NoteEntrepriseRapportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NoteEntrepriseRapportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    noteEntrepriseRapportService = TestBed.inject(NoteEntrepriseRapportService);
    entrepriseService = TestBed.inject(EntrepriseService);
    projetService = TestBed.inject(ProjetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entreprise query and add missing value', () => {
      const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 456 };
      const entreprise: IEntreprise = { id: 23490 };
      noteEntrepriseRapport.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 10358 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Projet query and add missing value', () => {
      const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 456 };
      const projet: IProjet = { id: 58162 };
      noteEntrepriseRapport.projet = projet;

      const projetCollection: IProjet[] = [{ id: 89368 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [projet];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(projetCollection, ...additionalProjets);
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 456 };
      const entreprise: IEntreprise = { id: 51012 };
      noteEntrepriseRapport.entreprise = entreprise;
      const projet: IProjet = { id: 89680 };
      noteEntrepriseRapport.projet = projet;

      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(noteEntrepriseRapport));
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.projetsSharedCollection).toContain(projet);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NoteEntrepriseRapport>>();
      const noteEntrepriseRapport = { id: 123 };
      jest.spyOn(noteEntrepriseRapportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: noteEntrepriseRapport }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(noteEntrepriseRapportService.update).toHaveBeenCalledWith(noteEntrepriseRapport);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NoteEntrepriseRapport>>();
      const noteEntrepriseRapport = new NoteEntrepriseRapport();
      jest.spyOn(noteEntrepriseRapportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: noteEntrepriseRapport }));
      saveSubject.complete();

      // THEN
      expect(noteEntrepriseRapportService.create).toHaveBeenCalledWith(noteEntrepriseRapport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NoteEntrepriseRapport>>();
      const noteEntrepriseRapport = { id: 123 };
      jest.spyOn(noteEntrepriseRapportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ noteEntrepriseRapport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(noteEntrepriseRapportService.update).toHaveBeenCalledWith(noteEntrepriseRapport);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
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
  });
});
