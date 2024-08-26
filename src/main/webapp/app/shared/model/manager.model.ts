import { IResource } from 'app/shared/model/resource.model';

export interface IManager {
  id?: number;
  name?: string;
  email?: string;
  resources?: IResource[] | null;
}

export const defaultValue: Readonly<IManager> = {};
