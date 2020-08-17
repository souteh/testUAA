import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { I18nTextTranslationService } from 'app/entities/i-18-n-text-translation/i-18-n-text-translation.service';
import { II18nTextTranslation, I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

describe('Service Tests', () => {
  describe('I18nTextTranslation Service', () => {
    let injector: TestBed;
    let service: I18nTextTranslationService;
    let httpMock: HttpTestingController;
    let elemDefault: II18nTextTranslation;
    let expectedResult: II18nTextTranslation | II18nTextTranslation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(I18nTextTranslationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new I18nTextTranslation(0, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a I18nTextTranslation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new I18nTextTranslation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a I18nTextTranslation', () => {
        const returnedFromService = Object.assign(
          {
            lang: 'BBBBBB',
            translation: 'BBBBBB',
            i18nTextId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of I18nTextTranslation', () => {
        const returnedFromService = Object.assign(
          {
            lang: 'BBBBBB',
            translation: 'BBBBBB',
            i18nTextId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a I18nTextTranslation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
