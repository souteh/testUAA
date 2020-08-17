import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { AlertemailUpdateComponent } from 'app/entities/alertemail/alertemail-update.component';
import { AlertemailService } from 'app/entities/alertemail/alertemail.service';
import { Alertemail } from 'app/shared/model/alertemail.model';

describe('Component Tests', () => {
  describe('Alertemail Management Update Component', () => {
    let comp: AlertemailUpdateComponent;
    let fixture: ComponentFixture<AlertemailUpdateComponent>;
    let service: AlertemailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [AlertemailUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertemailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertemailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertemailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alertemail(123);
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
        const entity = new Alertemail();
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
