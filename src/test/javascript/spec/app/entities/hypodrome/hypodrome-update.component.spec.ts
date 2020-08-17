import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { HypodromeUpdateComponent } from 'app/entities/hypodrome/hypodrome-update.component';
import { HypodromeService } from 'app/entities/hypodrome/hypodrome.service';
import { Hypodrome } from 'app/shared/model/hypodrome.model';

describe('Component Tests', () => {
  describe('Hypodrome Management Update Component', () => {
    let comp: HypodromeUpdateComponent;
    let fixture: ComponentFixture<HypodromeUpdateComponent>;
    let service: HypodromeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [HypodromeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HypodromeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HypodromeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HypodromeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Hypodrome(123);
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
        const entity = new Hypodrome();
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
