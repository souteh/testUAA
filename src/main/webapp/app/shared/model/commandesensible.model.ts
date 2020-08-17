export interface ICommandesensible {
  id?: number;
  idcommandesensible?: number;
  codecommandesensible?: string;
  libellecommandesensible?: string;
}

export class Commandesensible implements ICommandesensible {
  constructor(
    public id?: number,
    public idcommandesensible?: number,
    public codecommandesensible?: string,
    public libellecommandesensible?: string
  ) {}
}
