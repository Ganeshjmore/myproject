import dayjs from 'dayjs';
import { IResource } from 'app/shared/model/resource.model';
import { IObjective } from 'app/shared/model/objective.model';

export interface IComment {
  id?: number;
  text?: string;
  createdAt?: string;
  resource?: IResource | null;
  objective?: IObjective | null;
}

export const defaultValue: Readonly<IComment> = {};
