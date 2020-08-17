export interface ITypeterminal {
  id?: number;
  idtypeterminal?: number;
  codetypeterminal?: string;
  libelle?: string;
}

export class Typeterminal implements ITypeterminal {
  constructor(public id?: number, public idtypeterminal?: number, public codetypeterminal?: string, public libelle?: string) {}
}
