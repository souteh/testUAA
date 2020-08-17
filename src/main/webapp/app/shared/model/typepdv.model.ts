export interface ITypepdv {
  id?: number;
  idtypepdv?: number;
  reftypepdv?: string;
  type?: string;
  nbremaxterminaux?: number;
  plafondpostpaye?: number;
}

export class Typepdv implements ITypepdv {
  constructor(
    public id?: number,
    public idtypepdv?: number,
    public reftypepdv?: string,
    public type?: string,
    public nbremaxterminaux?: number,
    public plafondpostpaye?: number
  ) {}
}
