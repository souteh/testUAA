import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PaiementlotsService } from 'app/entities/paiementlots/paiementlots.service';
import { IPaiementlots, Paiementlots } from 'app/shared/model/paiementlots.model';

describe('Service Tests', () => {
  describe('Paiementlots Service', () => {
    let injector: TestBed;
    let service: PaiementlotsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaiementlots;
    let expectedResult: IPaiementlots | IPaiementlots[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaiementlotsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Paiementlots(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Paiementlots', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Paiementlots()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Paiementlots', () => {
        const returnedFromService = Object.assign(
          {
            idlotpaiement: 1,
            codepaiement: 'BBBBBB',
            type: 'BBBBBB',
            seuil: 1,
            montantavance: 1,
            delaipurge: 1,
            lieuautorise: 'BBBBBB',
            lieuannulation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Paiementlots', () => {
        const returnedFromService = Object.assign(
          {
            idlotpaiement: 1,
            codepaiement: 'BBBBBB',
            type: 'BBBBBB',
            seuil: 1,
            montantavance: 1,
            delaipurge: 1,
            lieuautorise: 'BBBBBB',
            lieuannulation: 'BBBBBB',
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

      it('should delete a Paiementlots', () => {
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
