import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { PaiementlotsUpdateComponent } from 'app/entities/paiementlots/paiementlots-update.component';
import { PaiementlotsService } from 'app/entities/paiementlots/paiementlots.service';
import { Paiementlots } from 'app/shared/model/paiementlots.model';

describe('Component Tests', () => {
  describe('Paiementlots Management Update Component', () => {
    let comp: PaiementlotsUpdateComponent;
    let fixture: ComponentFixture<PaiementlotsUpdateComponent>;
    let service: PaiementlotsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [PaiementlotsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaiementlotsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaiementlotsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaiementlotsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paiementlots(123);
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
        const entity = new Paiementlots();
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
