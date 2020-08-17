import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDelaiannulation } from 'app/shared/model/delaiannulation.model';

type EntityResponseType = HttpResponse<IDelaiannulation>;
type EntityArrayResponseType = HttpResponse<IDelaiannulation[]>;

@Injectable({ providedIn: 'root' })
export class DelaiannulationService {
  public resourceUrl = SERVER_API_URL + 'api/delaiannulations';

  constructor(protected http: HttpClient) {}

  create(delaiannulation: IDelaiannulation): Observable<EntityResponseType> {
    return this.http.post<IDelaiannulation>(this.resourceUrl, delaiannulation, { observe: 'response' });
  }

  update(delaiannulation: IDelaiannulation): Observable<EntityResponseType> {
    return this.http.put<IDelaiannulation>(this.resourceUrl, delaiannulation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDelaiannulation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDelaiannulation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
