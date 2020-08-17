import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { TestUaaTestModule } from '../../../test.module';
import { I18nTextTranslationComponent } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation.component';
import { I18nTextTranslationService } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation.service';
import { I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

describe('Component Tests', () => {
  describe('I18nTextTranslation Management Component', () => {
    let comp: I18nTextTranslationComponent;
    let fixture: ComponentFixture<I18nTextTranslationComponent>;
    let service: I18nTextTranslationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [I18nTextTranslationComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(I18nTextTranslationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(I18nTextTranslationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(I18nTextTranslationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new I18nTextTranslation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.i18nTextTranslations && comp.i18nTextTranslations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new I18nTextTranslation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.i18nTextTranslations && comp.i18nTextTranslations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
