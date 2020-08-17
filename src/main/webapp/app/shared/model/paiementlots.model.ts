export interface IPaiementlots {
  id?: number;
  idlotpaiement?: number;
  codepaiement?: string;
  type?: string;
  seuil?: number;
  montantavance?: number;
  delaipurge?: number;
  lieuautorise?: string;
  lieuannulation?: string;
}

export class Paiementlots implements IPaiementlots {
  constructor(
    public id?: number,
    public idlotpaiement?: number,
    public codepaiement?: string,
    public type?: string,
    public seuil?: number,
    public montantavance?: number,
    public delaipurge?: number,
    public lieuautorise?: string,
    public lieuannulation?: string
  ) {}
}
