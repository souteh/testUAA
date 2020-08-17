import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TypepdvService } from 'app/entities/typepdv/typepdv.service';
import { ITypepdv, Typepdv } from 'app/shared/model/typepdv.model';

describe('Service Tests', () => {
  describe('Typepdv Service', () => {
    let injector: TestBed;
    let service: TypepdvService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypepdv;
    let expectedResult: ITypepdv | ITypepdv[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TypepdvService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Typepdv(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Typepdv', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Typepdv()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Typepdv', () => {
        const returnedFromService = Object.assign(
          {
            idtypepdv: 1,
            reftypepdv: 'BBBBBB',
            type: 'BBBBBB',
            nbremaxterminaux: 1,
            plafondpostpaye: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Typepdv', () => {
        const returnedFromService = Object.assign(
          {
            idtypepdv: 1,
            reftypepdv: 'BBBBBB',
            type: 'BBBBBB',
            nbremaxterminaux: 1,
            plafondpostpaye: 1,
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

      it('should delete a Typepdv', () => {
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
