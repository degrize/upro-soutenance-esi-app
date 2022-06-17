import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INoteEntrepriseRapport, NoteEntrepriseRapport } from '../note-entreprise-rapport.model';

import { NoteEntrepriseRapportService } from './note-entreprise-rapport.service';

describe('NoteEntrepriseRapport Service', () => {
  let service: NoteEntrepriseRapportService;
  let httpMock: HttpTestingController;
  let elemDefault: INoteEntrepriseRapport;
  let expectedResult: INoteEntrepriseRapport | INoteEntrepriseRapport[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NoteEntrepriseRapportService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      note: 0,
      observation: 'AAAAAAA',
      dateAjout: currentDate,
      dateModification: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAjout: currentDate.format(DATE_FORMAT),
          dateModification: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a NoteEntrepriseRapport', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAjout: currentDate.format(DATE_FORMAT),
          dateModification: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAjout: currentDate,
          dateModification: currentDate,
        },
        returnedFromService
      );

      service.create(new NoteEntrepriseRapport()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NoteEntrepriseRapport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          note: 1,
          observation: 'BBBBBB',
          dateAjout: currentDate.format(DATE_FORMAT),
          dateModification: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAjout: currentDate,
          dateModification: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NoteEntrepriseRapport', () => {
      const patchObject = Object.assign(
        {
          note: 1,
          observation: 'BBBBBB',
          dateAjout: currentDate.format(DATE_FORMAT),
          dateModification: currentDate.format(DATE_FORMAT),
        },
        new NoteEntrepriseRapport()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAjout: currentDate,
          dateModification: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NoteEntrepriseRapport', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          note: 1,
          observation: 'BBBBBB',
          dateAjout: currentDate.format(DATE_FORMAT),
          dateModification: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAjout: currentDate,
          dateModification: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a NoteEntrepriseRapport', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNoteEntrepriseRapportToCollectionIfMissing', () => {
      it('should add a NoteEntrepriseRapport to an empty array', () => {
        const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 123 };
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing([], noteEntrepriseRapport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(noteEntrepriseRapport);
      });

      it('should not add a NoteEntrepriseRapport to an array that contains it', () => {
        const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 123 };
        const noteEntrepriseRapportCollection: INoteEntrepriseRapport[] = [
          {
            ...noteEntrepriseRapport,
          },
          { id: 456 },
        ];
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing(noteEntrepriseRapportCollection, noteEntrepriseRapport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NoteEntrepriseRapport to an array that doesn't contain it", () => {
        const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 123 };
        const noteEntrepriseRapportCollection: INoteEntrepriseRapport[] = [{ id: 456 }];
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing(noteEntrepriseRapportCollection, noteEntrepriseRapport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(noteEntrepriseRapport);
      });

      it('should add only unique NoteEntrepriseRapport to an array', () => {
        const noteEntrepriseRapportArray: INoteEntrepriseRapport[] = [{ id: 123 }, { id: 456 }, { id: 1948 }];
        const noteEntrepriseRapportCollection: INoteEntrepriseRapport[] = [{ id: 123 }];
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing(
          noteEntrepriseRapportCollection,
          ...noteEntrepriseRapportArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 123 };
        const noteEntrepriseRapport2: INoteEntrepriseRapport = { id: 456 };
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing([], noteEntrepriseRapport, noteEntrepriseRapport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(noteEntrepriseRapport);
        expect(expectedResult).toContain(noteEntrepriseRapport2);
      });

      it('should accept null and undefined values', () => {
        const noteEntrepriseRapport: INoteEntrepriseRapport = { id: 123 };
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing([], null, noteEntrepriseRapport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(noteEntrepriseRapport);
      });

      it('should return initial array if no NoteEntrepriseRapport is added', () => {
        const noteEntrepriseRapportCollection: INoteEntrepriseRapport[] = [{ id: 123 }];
        expectedResult = service.addNoteEntrepriseRapportToCollectionIfMissing(noteEntrepriseRapportCollection, undefined, null);
        expect(expectedResult).toEqual(noteEntrepriseRapportCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
