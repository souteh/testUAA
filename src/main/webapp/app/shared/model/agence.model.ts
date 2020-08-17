export interface IAgence {
  id?: number;
  idagence?: number;
  codeagence?: string;
  numero?: number;
  nom?: string;
  adresse?: string;
}

export class Agence implements IAgence {
  constructor(
    public id?: number,
    public idagence?: number,
    public codeagence?: string,
    public numero?: number,
    public nom?: string,
    public adresse?: string
  ) {}
}
