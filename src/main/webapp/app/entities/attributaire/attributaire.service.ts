import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttributaire } from 'app/shared/model/attributaire.model';

type EntityResponseType = HttpResponse<IAttributaire>;
type EntityArrayResponseType = HttpResponse<IAttributaire[]>;

@Injectable({ providedIn: 'root' })
export class AttributaireService {
  public resourceUrl = SERVER_API_URL + 'api/attributaires';

  constructor(protected http: HttpClient) {}

  create(attributaire: IAttributaire): Observable<EntityResponseType> {
    return this.http.post<IAttributaire>(this.resourceUrl, attributaire, { observe: 'response' });
  }

  update(attributaire: IAttributaire): Observable<EntityResponseType> {
    return this.http.put<IAttributaire>(this.resourceUrl, attributaire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttributaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttributaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
