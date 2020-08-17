import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestUaaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { I18nTextTranslationDeleteDialogComponent } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation-delete-dialog.component';
import { I18nTextTranslationService } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation.service';

describe('Component Tests', () => {
  describe('I18nTextTranslation Management Delete Component', () => {
    let comp: I18nTextTranslationDeleteDialogComponent;
    let fixture: ComponentFixture<I18nTextTranslationDeleteDialogComponent>;
    let service: I18nTextTranslationService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [I18nTextTranslationDeleteDialogComponent],
      })
        .overrideTemplate(I18nTextTranslationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(I18nTextTranslationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(I18nTextTranslationService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
