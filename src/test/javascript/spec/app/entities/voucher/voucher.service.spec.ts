import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VoucherService } from 'app/entities/voucher/voucher.service';
import { IVoucher, Voucher } from 'app/shared/model/voucher.model';

describe('Service Tests', () => {
  describe('Voucher Service', () => {
    let injector: TestBed;
    let service: VoucherService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoucher;
    let expectedResult: IVoucher | IVoucher[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VoucherService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Voucher(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Voucher', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Voucher()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Voucher', () => {
        const returnedFromService = Object.assign(
          {
            idvoucher: 1,
            codevoucher: 'BBBBBB',
            statut: 'BBBBBB',
            lieu: 'BBBBBB',
            seuil: 1,
            delaipurge: 1,
            plafond: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Voucher', () => {
        const returnedFromService = Object.assign(
          {
            idvoucher: 1,
            codevoucher: 'BBBBBB',
            statut: 'BBBBBB',
            lieu: 'BBBBBB',
            seuil: 1,
            delaipurge: 1,
            plafond: 1,
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

      it('should delete a Voucher', () => {
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
