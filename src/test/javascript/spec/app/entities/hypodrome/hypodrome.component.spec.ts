import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { TestUaaTestModule } from '../../../test.module';
import { HypodromeComponent } from 'app/entities/hypodrome/hypodrome.component';
import { HypodromeService } from 'app/entities/hypodrome/hypodrome.service';
import { Hypodrome } from 'app/shared/model/hypodrome.model';

describe('Component Tests', () => {
  describe('Hypodrome Management Component', () => {
    let comp: HypodromeComponent;
    let fixture: ComponentFixture<HypodromeComponent>;
    let service: HypodromeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [HypodromeComponent],
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
        .overrideTemplate(HypodromeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HypodromeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HypodromeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hypodrome(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hypodromes && comp.hypodromes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hypodrome(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hypodromes && comp.hypodromes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
