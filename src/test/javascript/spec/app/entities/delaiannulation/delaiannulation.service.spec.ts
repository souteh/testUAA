import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DelaiannulationService } from 'app/entities/delaiannulation/delaiannulation.service';
import { IDelaiannulation, Delaiannulation } from 'app/shared/model/delaiannulation.model';

describe('Service Tests', () => {
  describe('Delaiannulation Service', () => {
    let injector: TestBed;
    let service: DelaiannulationService;
    let httpMock: HttpTestingController;
    let elemDefault: IDelaiannulation;
    let expectedResult: IDelaiannulation | IDelaiannulation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DelaiannulationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Delaiannulation(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Delaiannulation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Delaiannulation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Delaiannulation', () => {
        const returnedFromService = Object.assign(
          {
            iddelaiannulation: 1,
            codedelaiannulation: 'BBBBBB',
            designationdelaiannulation: 'BBBBBB',
            valeurdelaiannulation: 1,
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Delaiannulation', () => {
        const returnedFromService = Object.assign(
          {
            iddelaiannulation: 1,
            codedelaiannulation: 'BBBBBB',
            designationdelaiannulation: 'BBBBBB',
            valeurdelaiannulation: 1,
            statut: 'BBBBBB',
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

      it('should delete a Delaiannulation', () => {
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
