import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVoucher } from 'app/shared/model/voucher.model';

type EntityResponseType = HttpResponse<IVoucher>;
type EntityArrayResponseType = HttpResponse<IVoucher[]>;

@Injectable({ providedIn: 'root' })
export class VoucherService {
  public resourceUrl = SERVER_API_URL + 'api/vouchers';

  constructor(protected http: HttpClient) {}

  create(voucher: IVoucher): Observable<EntityResponseType> {
    return this.http.post<IVoucher>(this.resourceUrl, voucher, { observe: 'response' });
  }

  update(voucher: IVoucher): Observable<EntityResponseType> {
    return this.http.put<IVoucher>(this.resourceUrl, voucher, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoucher[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
