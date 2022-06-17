import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAnneeAcademique, AnneeAcademique } from '../annee-academique.model';
import { AnneeAcademiqueService } from '../service/annee-academique.service';

import { AnneeAcademiqueRoutingResolveService } from './annee-academique-routing-resolve.service';

describe('AnneeAcademique routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AnneeAcademiqueRoutingResolveService;
  let service: AnneeAcademiqueService;
  let resultAnneeAcademique: IAnneeAcademique | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(AnneeAcademiqueRoutingResolveService);
    service = TestBed.inject(AnneeAcademiqueService);
    resultAnneeAcademique = undefined;
  });

  describe('resolve', () => {
    it('should return IAnneeAcademique returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnneeAcademique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnneeAcademique).toEqual({ id: 123 });
    });

    it('should return new IAnneeAcademique if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnneeAcademique = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnneeAcademique).toEqual(new AnneeAcademique());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AnneeAcademique })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnneeAcademique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnneeAcademique).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
