export interface IDelaiannulation {
  id?: number;
  iddelaiannulation?: number;
  codedelaiannulation?: string;
  designationdelaiannulation?: string;
  valeurdelaiannulation?: number;
  statut?: string;
}

export class Delaiannulation implements IDelaiannulation {
  constructor(
    public id?: number,
    public iddelaiannulation?: number,
    public codedelaiannulation?: string,
    public designationdelaiannulation?: string,
    public valeurdelaiannulation?: number,
    public statut?: string
  ) {}
}
