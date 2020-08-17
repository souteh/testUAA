import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { I18nTextTranslationUpdateComponent } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation-update.component';
import { I18nTextTranslationService } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation.service';
import { I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

describe('Component Tests', () => {
  describe('I18nTextTranslation Management Update Component', () => {
    let comp: I18nTextTranslationUpdateComponent;
    let fixture: ComponentFixture<I18nTextTranslationUpdateComponent>;
    let service: I18nTextTranslationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [I18nTextTranslationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(I18nTextTranslationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(I18nTextTranslationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(I18nTextTranslationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new I18nTextTranslation(123);
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
        const entity = new I18nTextTranslation();
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
