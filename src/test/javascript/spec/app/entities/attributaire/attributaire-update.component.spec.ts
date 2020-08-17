import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { AttributaireUpdateComponent } from 'app/entities/attributaire/attributaire-update.component';
import { AttributaireService } from 'app/entities/attributaire/attributaire.service';
import { Attributaire } from 'app/shared/model/attributaire.model';

describe('Component Tests', () => {
  describe('Attributaire Management Update Component', () => {
    let comp: AttributaireUpdateComponent;
    let fixture: ComponentFixture<AttributaireUpdateComponent>;
    let service: AttributaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [AttributaireUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttributaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttributaireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttributaireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Attributaire(123);
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
        const entity = new Attributaire();
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
