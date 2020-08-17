import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlertemail } from 'app/shared/model/alertemail.model';

type EntityResponseType = HttpResponse<IAlertemail>;
type EntityArrayResponseType = HttpResponse<IAlertemail[]>;

@Injectable({ providedIn: 'root' })
export class AlertemailService {
  public resourceUrl = SERVER_API_URL + 'api/alertemails';

  constructor(protected http: HttpClient) {}

  create(alertemail: IAlertemail): Observable<EntityResponseType> {
    return this.http.post<IAlertemail>(this.resourceUrl, alertemail, { observe: 'response' });
  }

  update(alertemail: IAlertemail): Observable<EntityResponseType> {
    return this.http.put<IAlertemail>(this.resourceUrl, alertemail, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlertemail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlertemail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
