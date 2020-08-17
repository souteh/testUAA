import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { PdvUpdateComponent } from 'app/entities/pdv/pdv-update.component';
import { PdvService } from 'app/entities/pdv/pdv.service';
import { Pdv } from 'app/shared/model/pdv.model';

describe('Component Tests', () => {
  describe('Pdv Management Update Component', () => {
    let comp: PdvUpdateComponent;
    let fixture: ComponentFixture<PdvUpdateComponent>;
    let service: PdvService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [PdvUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PdvUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PdvUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PdvService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pdv(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pdv();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
