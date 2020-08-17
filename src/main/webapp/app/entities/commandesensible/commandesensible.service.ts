import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandesensible } from 'app/shared/model/commandesensible.model';

type EntityResponseType = HttpResponse<ICommandesensible>;
type EntityArrayResponseType = HttpResponse<ICommandesensible[]>;

@Injectable({ providedIn: 'root' })
export class CommandesensibleService {
  public resourceUrl = SERVER_API_URL + 'api/commandesensibles';

  constructor(protected http: HttpClient) {}

  create(commandesensible: ICommandesensible): Observable<EntityResponseType> {
    return this.http.post<ICommandesensible>(this.resourceUrl, commandesensible, { observe: 'response' });
  }

  update(commandesensible: ICommandesensible): Observable<EntityResponseType> {
    return this.http.put<ICommandesensible>(this.resourceUrl, commandesensible, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommandesensible>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommandesensible[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
