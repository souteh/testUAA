import { IAttributaire } from 'app/shared/model/attributaire.model';

export interface IChoisit {
  id?: number;
  idproduit?: number;
  idattributaire?: IAttributaire;
}

export class Choisit implements IChoisit {
  constructor(public id?: number, public idproduit?: number, public idattributaire?: IAttributaire) {}
}
