import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { PaiementlotsDetailComponent } from 'app/entities/paiementlots/paiementlots-detail.component';
import { Paiementlots } from 'app/shared/model/paiementlots.model';

describe('Component Tests', () => {
  describe('Paiementlots Management Detail Component', () => {
    let comp: PaiementlotsDetailComponent;
    let fixture: ComponentFixture<PaiementlotsDetailComponent>;
    const route = ({ data: of({ paiementlots: new Paiementlots(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [PaiementlotsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaiementlotsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaiementlotsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paiementlots on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paiementlots).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
