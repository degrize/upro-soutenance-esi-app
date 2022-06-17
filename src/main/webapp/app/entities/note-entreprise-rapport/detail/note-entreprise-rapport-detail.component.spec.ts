import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NoteEntrepriseRapportDetailComponent } from './note-entreprise-rapport-detail.component';

describe('NoteEntrepriseRapport Management Detail Component', () => {
  let comp: NoteEntrepriseRapportDetailComponent;
  let fixture: ComponentFixture<NoteEntrepriseRapportDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NoteEntrepriseRapportDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ noteEntrepriseRapport: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NoteEntrepriseRapportDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NoteEntrepriseRapportDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load noteEntrepriseRapport on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.noteEntrepriseRapport).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
