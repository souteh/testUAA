export interface IPdv {
  id?: number;
  numero?: number;
  agence?: string;
  designation?: string;
  ville?: string;
  statut?: string;
}

export class Pdv implements IPdv {
  constructor(
    public id?: number,
    public numero?: number,
    public agence?: string,
    public designation?: string,
    public ville?: string,
    public statut?: string
  ) {}
}
