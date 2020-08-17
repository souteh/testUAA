import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { I18nTextTranslationDetailComponent } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation-detail.component';
import { I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

describe('Component Tests', () => {
  describe('I18nTextTranslation Management Detail Component', () => {
    let comp: I18nTextTranslationDetailComponent;
    let fixture: ComponentFixture<I18nTextTranslationDetailComponent>;
    const route = ({ data: of({ i18nTextTranslation: new I18nTextTranslation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [I18nTextTranslationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(I18nTextTranslationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(I18nTextTranslationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load i18nTextTranslation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.i18nTextTranslation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
