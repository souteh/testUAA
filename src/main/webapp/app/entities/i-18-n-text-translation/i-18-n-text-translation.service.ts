import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { II18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

type EntityResponseType = HttpResponse<II18nTextTranslation>;
type EntityArrayResponseType = HttpResponse<II18nTextTranslation[]>;

@Injectable({ providedIn: 'root' })
export class I18nTextTranslationService {
  public resourceUrl = SERVER_API_URL + 'api/i-18-n-text-translations';

  constructor(protected http: HttpClient) {}

  create(i18nTextTranslation: II18nTextTranslation): Observable<EntityResponseType> {
    return this.http.post<II18nTextTranslation>(this.resourceUrl, i18nTextTranslation, { observe: 'response' });
  }

  update(i18nTextTranslation: II18nTextTranslation): Observable<EntityResponseType> {
    return this.http.put<II18nTextTranslation>(this.resourceUrl, i18nTextTranslation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<II18nTextTranslation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<II18nTextTranslation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
