import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPdv } from 'app/shared/model/pdv.model';

type EntityResponseType = HttpResponse<IPdv>;
type EntityArrayResponseType = HttpResponse<IPdv[]>;

@Injectable({ providedIn: 'root' })
export class PdvService {
  public resourceUrl = SERVER_API_URL + 'api/pdvs';

  constructor(protected http: HttpClient) {}

  create(pdv: IPdv): Observable<EntityResponseType> {
    return this.http.post<IPdv>(this.resourceUrl, pdv, { observe: 'response' });
  }

  update(pdv: IPdv): Observable<EntityResponseType> {
    return this.http.put<IPdv>(this.resourceUrl, pdv, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPdv>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPdv[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
