import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { SmtpDetailComponent } from 'app/entities/smtp/smtp-detail.component';
import { Smtp } from 'app/shared/model/smtp.model';

describe('Component Tests', () => {
  describe('Smtp Management Detail Component', () => {
    let comp: SmtpDetailComponent;
    let fixture: ComponentFixture<SmtpDetailComponent>;
    const route = ({ data: of({ smtp: new Smtp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [SmtpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SmtpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SmtpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load smtp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.smtp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
