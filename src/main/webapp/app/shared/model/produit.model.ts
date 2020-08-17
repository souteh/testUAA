export interface IProduit {
  id?: number;
  idproduit?: number;
  designation?: string;
  libelle?: string;
  codeproduit?: string;
  enjeumin?: number;
  ordre?: string;
  nbrechevauxbase?: number;
  nbreminpartant?: number;
  champx?: number;
  express?: number;
  appartenance?: string;
  enjeumax?: number;
  statut?: string;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public idproduit?: number,
    public designation?: string,
    public libelle?: string,
    public codeproduit?: string,
    public enjeumin?: number,
    public ordre?: string,
    public nbrechevauxbase?: number,
    public nbreminpartant?: number,
    public champx?: number,
    public express?: number,
    public appartenance?: string,
    public enjeumax?: number,
    public statut?: string
  ) {}
}
