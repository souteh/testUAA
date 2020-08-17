export interface IVoucher {
  id?: number;
  idvoucher?: number;
  codevoucher?: string;
  statut?: string;
  lieu?: string;
  seuil?: number;
  delaipurge?: number;
  plafond?: number;
}

export class Voucher implements IVoucher {
  constructor(
    public id?: number,
    public idvoucher?: number,
    public codevoucher?: string,
    public statut?: string,
    public lieu?: string,
    public seuil?: number,
    public delaipurge?: number,
    public plafond?: number
  ) {}
}
