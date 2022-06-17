import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INoteEntrepriseRapport, NoteEntrepriseRapport } from '../note-entreprise-rapport.model';
import { NoteEntrepriseRapportService } from '../service/note-entreprise-rapport.service';

import { NoteEntrepriseRapportRoutingResolveService } from './note-entreprise-rapport-routing-resolve.service';

describe('NoteEntrepriseRapport routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NoteEntrepriseRapportRoutingResolveService;
  let service: NoteEntrepriseRapportService;
  let resultNoteEntrepriseRapport: INoteEntrepriseRapport | undefined;

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
    routingResolveService = TestBed.inject(NoteEntrepriseRapportRoutingResolveService);
    service = TestBed.inject(NoteEntrepriseRapportService);
    resultNoteEntrepriseRapport = undefined;
  });

  describe('resolve', () => {
    it('should return INoteEntrepriseRapport returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNoteEntrepriseRapport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNoteEntrepriseRapport).toEqual({ id: 123 });
    });

    it('should return new INoteEntrepriseRapport if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNoteEntrepriseRapport = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNoteEntrepriseRapport).toEqual(new NoteEntrepriseRapport());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NoteEntrepriseRapport })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNoteEntrepriseRapport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNoteEntrepriseRapport).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
