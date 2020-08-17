export interface IAttributaire {
  id?: number;
  idattributaire?: number;
  libelle?: string;
  codeattributaire?: string;
}

export class Attributaire implements IAttributaire {
  constructor(public id?: number, public idattributaire?: number, public libelle?: string, public codeattributaire?: string) {}
}
