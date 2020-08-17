export interface II18nTextTranslation {
  id?: number;
  lang?: string;
  translation?: string;
  i18nTextId?: number;
}

export class I18nTextTranslation implements II18nTextTranslation {
  constructor(public id?: number, public lang?: string, public translation?: string, public i18nTextId?: number) {}
}
