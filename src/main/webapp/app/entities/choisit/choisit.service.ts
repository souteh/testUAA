import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChoisit } from 'app/shared/model/choisit.model';

type EntityResponseType = HttpResponse<IChoisit>;
type EntityArrayResponseType = HttpResponse<IChoisit[]>;

@Injectable({ providedIn: 'root' })
export class ChoisitService {
  public resourceUrl = SERVER_API_URL + 'api/choisits';

  constructor(protected http: HttpClient) {}

  create(choisit: IChoisit): Observable<EntityResponseType> {
    return this.http.post<IChoisit>(this.resourceUrl, choisit, { observe: 'response' });
  }

  update(choisit: IChoisit): Observable<EntityResponseType> {
    return this.http.put<IChoisit>(this.resourceUrl, choisit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChoisit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChoisit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
