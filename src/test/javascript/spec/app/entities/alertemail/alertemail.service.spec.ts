import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AlertemailService } from 'app/entities/alertemail/alertemail.service';
import { IAlertemail, Alertemail } from 'app/shared/model/alertemail.model';

describe('Service Tests', () => {
  describe('Alertemail Service', () => {
    let injector: TestBed;
    let service: AlertemailService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlertemail;
    let expectedResult: IAlertemail | IAlertemail[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlertemailService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Alertemail(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Alertemail', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Alertemail()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Alertemail', () => {
        const returnedFromService = Object.assign(
          {
            idalertemail: 1,
            codealertemail: 'BBBBBB',
            typealertemail: 'BBBBBB',
            objetalertemail: 'BBBBBB',
            adressemaildiffusion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Alertemail', () => {
        const returnedFromService = Object.assign(
          {
            idalertemail: 1,
            codealertemail: 'BBBBBB',
            typealertemail: 'BBBBBB',
            objetalertemail: 'BBBBBB',
            adressemaildiffusion: 'BBBBBB',
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

      it('should delete a Alertemail', () => {
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
