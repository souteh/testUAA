import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { PdvDetailComponent } from 'app/entities/pdv/pdv-detail.component';
import { Pdv } from 'app/shared/model/pdv.model';

describe('Component Tests', () => {
  describe('Pdv Management Detail Component', () => {
    let comp: PdvDetailComponent;
    let fixture: ComponentFixture<PdvDetailComponent>;
    const route = ({ data: of({ pdv: new Pdv(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [PdvDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PdvDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PdvDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pdv on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pdv).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
