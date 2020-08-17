import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { TypepdvDetailComponent } from 'app/entities/typepdv/typepdv-detail.component';
import { Typepdv } from 'app/shared/model/typepdv.model';

describe('Component Tests', () => {
  describe('Typepdv Management Detail Component', () => {
    let comp: TypepdvDetailComponent;
    let fixture: ComponentFixture<TypepdvDetailComponent>;
    const route = ({ data: of({ typepdv: new Typepdv(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [TypepdvDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypepdvDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypepdvDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typepdv on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typepdv).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
