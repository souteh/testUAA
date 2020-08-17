import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { AlertemailDetailComponent } from 'app/entities/alertemail/alertemail-detail.component';
import { Alertemail } from 'app/shared/model/alertemail.model';

describe('Component Tests', () => {
  describe('Alertemail Management Detail Component', () => {
    let comp: AlertemailDetailComponent;
    let fixture: ComponentFixture<AlertemailDetailComponent>;
    const route = ({ data: of({ alertemail: new Alertemail(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [AlertemailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertemailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertemailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alertemail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alertemail).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
