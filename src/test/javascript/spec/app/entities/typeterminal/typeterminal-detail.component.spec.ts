import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { TypeterminalDetailComponent } from 'app/entities/typeterminal/typeterminal-detail.component';
import { Typeterminal } from 'app/shared/model/typeterminal.model';

describe('Component Tests', () => {
  describe('Typeterminal Management Detail Component', () => {
    let comp: TypeterminalDetailComponent;
    let fixture: ComponentFixture<TypeterminalDetailComponent>;
    const route = ({ data: of({ typeterminal: new Typeterminal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [TypeterminalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeterminalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeterminalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeterminal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeterminal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
