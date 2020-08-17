import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { DelaiannulationDetailComponent } from 'app/entities/delaiannulation/delaiannulation-detail.component';
import { Delaiannulation } from 'app/shared/model/delaiannulation.model';

describe('Component Tests', () => {
  describe('Delaiannulation Management Detail Component', () => {
    let comp: DelaiannulationDetailComponent;
    let fixture: ComponentFixture<DelaiannulationDetailComponent>;
    const route = ({ data: of({ delaiannulation: new Delaiannulation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [DelaiannulationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DelaiannulationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DelaiannulationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load delaiannulation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.delaiannulation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
