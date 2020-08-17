import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SmtpService } from 'app/entities/smtp/smtp.service';
import { ISmtp, Smtp } from 'app/shared/model/smtp.model';

describe('Service Tests', () => {
  describe('Smtp Service', () => {
    let injector: TestBed;
    let service: SmtpService;
    let httpMock: HttpTestingController;
    let elemDefault: ISmtp;
    let expectedResult: ISmtp | ISmtp[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SmtpService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Smtp(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Smtp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Smtp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Smtp', () => {
        const returnedFromService = Object.assign(
          {
            idsmtp: 1,
            codesmtp: 'BBBBBB',
            adresseipsmtp: 'BBBBBB',
            authentification: 'BBBBBB',
            statut: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Smtp', () => {
        const returnedFromService = Object.assign(
          {
            idsmtp: 1,
            codesmtp: 'BBBBBB',
            adresseipsmtp: 'BBBBBB',
            authentification: 'BBBBBB',
            statut: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
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

      it('should delete a Smtp', () => {
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
