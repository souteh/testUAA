import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { AttributaireDetailComponent } from 'app/entities/attributaire/attributaire-detail.component';
import { Attributaire } from 'app/shared/model/attributaire.model';

describe('Component Tests', () => {
  describe('Attributaire Management Detail Component', () => {
    let comp: AttributaireDetailComponent;
    let fixture: ComponentFixture<AttributaireDetailComponent>;
    const route = ({ data: of({ attributaire: new Attributaire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [AttributaireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttributaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttributaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attributaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attributaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
