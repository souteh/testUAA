import { Moment } from 'moment';
import { ITypeterminal } from 'app/shared/model/typeterminal.model';

export interface IVersion {
  id?: number;
  idversion?: number;
  refversion?: string;
  libelle?: string;
  date?: Moment;
  fichier?: string;
  idtypeterminal?: ITypeterminal;
}

export class Version implements IVersion {
  constructor(
    public id?: number,
    public idversion?: number,
    public refversion?: string,
    public libelle?: string,
    public date?: Moment,
    public fichier?: string,
    public idtypeterminal?: ITypeterminal
  ) {}
}
