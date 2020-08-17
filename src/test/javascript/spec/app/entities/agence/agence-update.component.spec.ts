import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { AgenceUpdateComponent } from 'app/entities/agence/agence-update.component';
import { AgenceService } from 'app/entities/agence/agence.service';
import { Agence } from 'app/shared/model/agence.model';

describe('Component Tests', () => {
  describe('Agence Management Update Component', () => {
    let comp: AgenceUpdateComponent;
    let fixture: ComponentFixture<AgenceUpdateComponent>;
    let service: AgenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [AgenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AgenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agence(123);
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
        const entity = new Agence();
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
