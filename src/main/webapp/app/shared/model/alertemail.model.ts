export interface IAlertemail {
  id?: number;
  idalertemail?: number;
  codealertemail?: string;
  typealertemail?: string;
  objetalertemail?: string;
  adressemaildiffusion?: string;
}

export class Alertemail implements IAlertemail {
  constructor(
    public id?: number,
    public idalertemail?: number,
    public codealertemail?: string,
    public typealertemail?: string,
    public objetalertemail?: string,
    public adressemaildiffusion?: string
  ) {}
}
