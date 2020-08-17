import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeterminal } from 'app/shared/model/typeterminal.model';

type EntityResponseType = HttpResponse<ITypeterminal>;
type EntityArrayResponseType = HttpResponse<ITypeterminal[]>;

@Injectable({ providedIn: 'root' })
export class TypeterminalService {
  public resourceUrl = SERVER_API_URL + 'api/typeterminals';

  constructor(protected http: HttpClient) {}

  create(typeterminal: ITypeterminal): Observable<EntityResponseType> {
    return this.http.post<ITypeterminal>(this.resourceUrl, typeterminal, { observe: 'response' });
  }

  update(typeterminal: ITypeterminal): Observable<EntityResponseType> {
    return this.http.put<ITypeterminal>(this.resourceUrl, typeterminal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeterminal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeterminal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
