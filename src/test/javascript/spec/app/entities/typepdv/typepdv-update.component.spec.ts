import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { TypepdvUpdateComponent } from 'app/entities/typepdv/typepdv-update.component';
import { TypepdvService } from 'app/entities/typepdv/typepdv.service';
import { Typepdv } from 'app/shared/model/typepdv.model';

describe('Component Tests', () => {
  describe('Typepdv Management Update Component', () => {
    let comp: TypepdvUpdateComponent;
    let fixture: ComponentFixture<TypepdvUpdateComponent>;
    let service: TypepdvService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [TypepdvUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypepdvUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypepdvUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypepdvService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Typepdv(123);
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
        const entity = new Typepdv();
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
