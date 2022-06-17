import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JuryService } from '../service/jury.service';
import { IJury, Jury } from '../jury.model';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { AnneeAcademiqueService } from 'app/entities/annee-academique/service/annee-academique.service';
import { IGenie } from 'app/entities/genie/genie.model';
import { GenieService } from 'app/entities/genie/service/genie.service';

import { JuryUpdateComponent } from './jury-update.component';

describe('Jury Management Update Component', () => {
  let comp: JuryUpdateComponent;
  let fixture: ComponentFixture<JuryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let juryService: JuryService;
  let anneeAcademiqueService: AnneeAcademiqueService;
  let genieService: GenieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JuryUpdateComponent],
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
      .overrideTemplate(JuryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JuryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    juryService = TestBed.inject(JuryService);
    anneeAcademiqueService = TestBed.inject(AnneeAcademiqueService);
    genieService = TestBed.inject(GenieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnneeAcademique query and add missing value', () => {
      const jury: IJury = { id: 456 };
      const anneeAcademique: IAnneeAcademique = { id: 8732 };
      jury.anneeAcademique = anneeAcademique;

      const anneeAcademiqueCollection: IAnneeAcademique[] = [{ id: 26539 }];
      jest.spyOn(anneeAcademiqueService, 'query').mockReturnValue(of(new HttpResponse({ body: anneeAcademiqueCollection })));
      const additionalAnneeAcademiques = [anneeAcademique];
      const expectedCollection: IAnneeAcademique[] = [...additionalAnneeAcademiques, ...anneeAcademiqueCollection];
      jest.spyOn(anneeAcademiqueService, 'addAnneeAcademiqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      expect(anneeAcademiqueService.query).toHaveBeenCalled();
      expect(anneeAcademiqueService.addAnneeAcademiqueToCollectionIfMissing).toHaveBeenCalledWith(
        anneeAcademiqueCollection,
        ...additionalAnneeAcademiques
      );
      expect(comp.anneeAcademiquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Genie query and add missing value', () => {
      const jury: IJury = { id: 456 };
      const genie: IGenie = { id: 26858 };
      jury.genie = genie;

      const genieCollection: IGenie[] = [{ id: 19555 }];
      jest.spyOn(genieService, 'query').mockReturnValue(of(new HttpResponse({ body: genieCollection })));
      const additionalGenies = [genie];
      const expectedCollection: IGenie[] = [...additionalGenies, ...genieCollection];
      jest.spyOn(genieService, 'addGenieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      expect(genieService.query).toHaveBeenCalled();
      expect(genieService.addGenieToCollectionIfMissing).toHaveBeenCalledWith(genieCollection, ...additionalGenies);
      expect(comp.geniesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const jury: IJury = { id: 456 };
      const anneeAcademique: IAnneeAcademique = { id: 16428 };
      jury.anneeAcademique = anneeAcademique;
      const genie: IGenie = { id: 96260 };
      jury.genie = genie;

      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(jury));
      expect(comp.anneeAcademiquesSharedCollection).toContain(anneeAcademique);
      expect(comp.geniesSharedCollection).toContain(genie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jury>>();
      const jury = { id: 123 };
      jest.spyOn(juryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jury }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(juryService.update).toHaveBeenCalledWith(jury);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jury>>();
      const jury = new Jury();
      jest.spyOn(juryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jury }));
      saveSubject.complete();

      // THEN
      expect(juryService.create).toHaveBeenCalledWith(jury);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Jury>>();
      const jury = { id: 123 };
      jest.spyOn(juryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jury });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(juryService.update).toHaveBeenCalledWith(jury);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAnneeAcademiqueById', () => {
      it('Should return tracked AnneeAcademique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAnneeAcademiqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGenieById', () => {
      it('Should return tracked Genie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGenieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
