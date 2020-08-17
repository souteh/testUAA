import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { ChoisitUpdateComponent } from 'app/entities/choisit/choisit-update.component';
import { ChoisitService } from 'app/entities/choisit/choisit.service';
import { Choisit } from 'app/shared/model/choisit.model';

describe('Component Tests', () => {
  describe('Choisit Management Update Component', () => {
    let comp: ChoisitUpdateComponent;
    let fixture: ComponentFixture<ChoisitUpdateComponent>;
    let service: ChoisitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [ChoisitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ChoisitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChoisitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChoisitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Choisit(123);
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
        const entity = new Choisit();
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
