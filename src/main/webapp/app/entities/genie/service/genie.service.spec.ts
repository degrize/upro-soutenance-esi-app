import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGenie, Genie } from '../genie.model';

import { GenieService } from './genie.service';

describe('Genie Service', () => {
  let service: GenieService;
  let httpMock: HttpTestingController;
  let elemDefault: IGenie;
  let expectedResult: IGenie | IGenie[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GenieService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      nomDirecteur: 'AAAAAAA',
      contactDirecteur: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Genie', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Genie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Genie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          nomDirecteur: 'BBBBBB',
          contactDirecteur: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Genie', () => {
      const patchObject = Object.assign(
        {
          nom: 'BBBBBB',
          nomDirecteur: 'BBBBBB',
        },
        new Genie()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Genie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          nomDirecteur: 'BBBBBB',
          contactDirecteur: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Genie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGenieToCollectionIfMissing', () => {
      it('should add a Genie to an empty array', () => {
        const genie: IGenie = { id: 123 };
        expectedResult = service.addGenieToCollectionIfMissing([], genie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(genie);
      });

      it('should not add a Genie to an array that contains it', () => {
        const genie: IGenie = { id: 123 };
        const genieCollection: IGenie[] = [
          {
            ...genie,
          },
          { id: 456 },
        ];
        expectedResult = service.addGenieToCollectionIfMissing(genieCollection, genie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Genie to an array that doesn't contain it", () => {
        const genie: IGenie = { id: 123 };
        const genieCollection: IGenie[] = [{ id: 456 }];
        expectedResult = service.addGenieToCollectionIfMissing(genieCollection, genie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(genie);
      });

      it('should add only unique Genie to an array', () => {
        const genieArray: IGenie[] = [{ id: 123 }, { id: 456 }, { id: 8748 }];
        const genieCollection: IGenie[] = [{ id: 123 }];
        expectedResult = service.addGenieToCollectionIfMissing(genieCollection, ...genieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const genie: IGenie = { id: 123 };
        const genie2: IGenie = { id: 456 };
        expectedResult = service.addGenieToCollectionIfMissing([], genie, genie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(genie);
        expect(expectedResult).toContain(genie2);
      });

      it('should accept null and undefined values', () => {
        const genie: IGenie = { id: 123 };
        expectedResult = service.addGenieToCollectionIfMissing([], null, genie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(genie);
      });

      it('should return initial array if no Genie is added', () => {
        const genieCollection: IGenie[] = [{ id: 123 }];
        expectedResult = service.addGenieToCollectionIfMissing(genieCollection, undefined, null);
        expect(expectedResult).toEqual(genieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
