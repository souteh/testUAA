export interface IPays {
  id?: number;
  idpays?: number;
  codepays?: string;
  designation?: string;
}

export class Pays implements IPays {
  constructor(public id?: number, public idpays?: number, public codepays?: string, public designation?: string) {}
}
