import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { ChoisitDetailComponent } from 'app/entities/choisit/choisit-detail.component';
import { Choisit } from 'app/shared/model/choisit.model';

describe('Component Tests', () => {
  describe('Choisit Management Detail Component', () => {
    let comp: ChoisitDetailComponent;
    let fixture: ComponentFixture<ChoisitDetailComponent>;
    const route = ({ data: of({ choisit: new Choisit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [ChoisitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ChoisitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChoisitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load choisit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.choisit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
