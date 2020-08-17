export interface IHypodrome {
  id?: number;
  idhypodrome?: number;
  codehypodrome?: number;
  abreviation?: string;
  pays?: string;
}

export class Hypodrome implements IHypodrome {
  constructor(
    public id?: number,
    public idhypodrome?: number,
    public codehypodrome?: number,
    public abreviation?: string,
    public pays?: string
  ) {}
}
