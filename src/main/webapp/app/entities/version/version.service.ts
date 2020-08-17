import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVersion } from 'app/shared/model/version.model';

type EntityResponseType = HttpResponse<IVersion>;
type EntityArrayResponseType = HttpResponse<IVersion[]>;

@Injectable({ providedIn: 'root' })
export class VersionService {
  public resourceUrl = SERVER_API_URL + 'api/versions';

  constructor(protected http: HttpClient) {}

  create(version: IVersion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(version);
    return this.http
      .post<IVersion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(version: IVersion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(version);
    return this.http
      .put<IVersion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVersion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVersion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(version: IVersion): IVersion {
    const copy: IVersion = Object.assign({}, version, {
      date: version.date && version.date.isValid() ? version.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((version: IVersion) => {
        version.date = version.date ? moment(version.date) : undefined;
      });
    }
    return res;
  }
}
