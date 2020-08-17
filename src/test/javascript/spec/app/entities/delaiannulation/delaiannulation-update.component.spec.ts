import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { DelaiannulationUpdateComponent } from 'app/entities/delaiannulation/delaiannulation-update.component';
import { DelaiannulationService } from 'app/entities/delaiannulation/delaiannulation.service';
import { Delaiannulation } from 'app/shared/model/delaiannulation.model';

describe('Component Tests', () => {
  describe('Delaiannulation Management Update Component', () => {
    let comp: DelaiannulationUpdateComponent;
    let fixture: ComponentFixture<DelaiannulationUpdateComponent>;
    let service: DelaiannulationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [DelaiannulationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DelaiannulationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelaiannulationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelaiannulationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Delaiannulation(123);
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
        const entity = new Delaiannulation();
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
