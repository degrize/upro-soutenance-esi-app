import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpecialiteService } from '../service/specialite.service';
import { ISpecialite, Specialite } from '../specialite.model';

import { SpecialiteUpdateComponent } from './specialite-update.component';

describe('Specialite Management Update Component', () => {
  let comp: SpecialiteUpdateComponent;
  let fixture: ComponentFixture<SpecialiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specialiteService: SpecialiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecialiteUpdateComponent],
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
      .overrideTemplate(SpecialiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecialiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specialiteService = TestBed.inject(SpecialiteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const specialite: ISpecialite = { id: 456 };

      activatedRoute.data = of({ specialite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(specialite));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialite>>();
      const specialite = { id: 123 };
      jest.spyOn(specialiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(specialiteService.update).toHaveBeenCalledWith(specialite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialite>>();
      const specialite = new Specialite();
      jest.spyOn(specialiteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialite }));
      saveSubject.complete();

      // THEN
      expect(specialiteService.create).toHaveBeenCalledWith(specialite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialite>>();
      const specialite = { id: 123 };
      jest.spyOn(specialiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specialiteService.update).toHaveBeenCalledWith(specialite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
