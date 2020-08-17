import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHypodrome } from 'app/shared/model/hypodrome.model';

type EntityResponseType = HttpResponse<IHypodrome>;
type EntityArrayResponseType = HttpResponse<IHypodrome[]>;

@Injectable({ providedIn: 'root' })
export class HypodromeService {
  public resourceUrl = SERVER_API_URL + 'api/hypodromes';

  constructor(protected http: HttpClient) {}

  create(hypodrome: IHypodrome): Observable<EntityResponseType> {
    return this.http.post<IHypodrome>(this.resourceUrl, hypodrome, { observe: 'response' });
  }

  update(hypodrome: IHypodrome): Observable<EntityResponseType> {
    return this.http.put<IHypodrome>(this.resourceUrl, hypodrome, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHypodrome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHypodrome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
