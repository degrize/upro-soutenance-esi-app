import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GenieService } from '../service/genie.service';
import { IGenie, Genie } from '../genie.model';

import { GenieUpdateComponent } from './genie-update.component';

describe('Genie Management Update Component', () => {
  let comp: GenieUpdateComponent;
  let fixture: ComponentFixture<GenieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let genieService: GenieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GenieUpdateComponent],
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
      .overrideTemplate(GenieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GenieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    genieService = TestBed.inject(GenieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const genie: IGenie = { id: 456 };

      activatedRoute.data = of({ genie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(genie));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genie>>();
      const genie = { id: 123 };
      jest.spyOn(genieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(genieService.update).toHaveBeenCalledWith(genie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genie>>();
      const genie = new Genie();
      jest.spyOn(genieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genie }));
      saveSubject.complete();

      // THEN
      expect(genieService.create).toHaveBeenCalledWith(genie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genie>>();
      const genie = { id: 123 };
      jest.spyOn(genieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(genieService.update).toHaveBeenCalledWith(genie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
