export interface IVille {
  id?: number;
  idville?: number;
  designation?: number;
  codeville?: string;
}

export class Ville implements IVille {
  constructor(public id?: number, public idville?: number, public designation?: number, public codeville?: string) {}
}
