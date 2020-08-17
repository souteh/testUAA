import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { TypeterminalUpdateComponent } from 'app/entities/typeterminal/typeterminal-update.component';
import { TypeterminalService } from 'app/entities/typeterminal/typeterminal.service';
import { Typeterminal } from 'app/shared/model/typeterminal.model';

describe('Component Tests', () => {
  describe('Typeterminal Management Update Component', () => {
    let comp: TypeterminalUpdateComponent;
    let fixture: ComponentFixture<TypeterminalUpdateComponent>;
    let service: TypeterminalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [TypeterminalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeterminalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeterminalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeterminalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Typeterminal(123);
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
        const entity = new Typeterminal();
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
