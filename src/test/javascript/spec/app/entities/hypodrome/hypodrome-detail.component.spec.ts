import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { HypodromeDetailComponent } from 'app/entities/hypodrome/hypodrome-detail.component';
import { Hypodrome } from 'app/shared/model/hypodrome.model';

describe('Component Tests', () => {
  describe('Hypodrome Management Detail Component', () => {
    let comp: HypodromeDetailComponent;
    let fixture: ComponentFixture<HypodromeDetailComponent>;
    const route = ({ data: of({ hypodrome: new Hypodrome(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [HypodromeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HypodromeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HypodromeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hypodrome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hypodrome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
