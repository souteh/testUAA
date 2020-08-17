import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { SmtpUpdateComponent } from 'app/entities/smtp/smtp-update.component';
import { SmtpService } from 'app/entities/smtp/smtp.service';
import { Smtp } from 'app/shared/model/smtp.model';

describe('Component Tests', () => {
  describe('Smtp Management Update Component', () => {
    let comp: SmtpUpdateComponent;
    let fixture: ComponentFixture<SmtpUpdateComponent>;
    let service: SmtpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [SmtpUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SmtpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SmtpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SmtpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Smtp(123);
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
        const entity = new Smtp();
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
