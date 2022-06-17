import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnneeAcademiqueService } from '../service/annee-academique.service';
import { IAnneeAcademique, AnneeAcademique } from '../annee-academique.model';

import { AnneeAcademiqueUpdateComponent } from './annee-academique-update.component';

describe('AnneeAcademique Management Update Component', () => {
  let comp: AnneeAcademiqueUpdateComponent;
  let fixture: ComponentFixture<AnneeAcademiqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anneeAcademiqueService: AnneeAcademiqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnneeAcademiqueUpdateComponent],
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
      .overrideTemplate(AnneeAcademiqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnneeAcademiqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anneeAcademiqueService = TestBed.inject(AnneeAcademiqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const anneeAcademique: IAnneeAcademique = { id: 456 };

      activatedRoute.data = of({ anneeAcademique });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(anneeAcademique));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AnneeAcademique>>();
      const anneeAcademique = { id: 123 };
      jest.spyOn(anneeAcademiqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anneeAcademique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anneeAcademique }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(anneeAcademiqueService.update).toHaveBeenCalledWith(anneeAcademique);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AnneeAcademique>>();
      const anneeAcademique = new AnneeAcademique();
      jest.spyOn(anneeAcademiqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anneeAcademique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anneeAcademique }));
      saveSubject.complete();

      // THEN
      expect(anneeAcademiqueService.create).toHaveBeenCalledWith(anneeAcademique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AnneeAcademique>>();
      const anneeAcademique = { id: 123 };
      jest.spyOn(anneeAcademiqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anneeAcademique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anneeAcademiqueService.update).toHaveBeenCalledWith(anneeAcademique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
