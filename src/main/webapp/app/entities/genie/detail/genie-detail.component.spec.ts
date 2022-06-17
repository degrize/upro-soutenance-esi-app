import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GenieDetailComponent } from './genie-detail.component';

describe('Genie Management Detail Component', () => {
  let comp: GenieDetailComponent;
  let fixture: ComponentFixture<GenieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GenieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ genie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GenieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GenieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load genie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.genie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
