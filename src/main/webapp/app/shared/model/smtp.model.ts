export interface ISmtp {
  id?: number;
  idsmtp?: number;
  codesmtp?: string;
  adresseipsmtp?: string;
  authentification?: string;
  statut?: string;
  login?: string;
  password?: string;
}

export class Smtp implements ISmtp {
  constructor(
    public id?: number,
    public idsmtp?: number,
    public codesmtp?: string,
    public adresseipsmtp?: string,
    public authentification?: string,
    public statut?: string,
    public login?: string,
    public password?: string
  ) {}
}
