import dayjs from 'dayjs';
import { IResource } from 'app/shared/model/resource.model';
import { IObjective } from 'app/shared/model/objective.model';
import { RatingLevel } from 'app/shared/model/enumerations/rating-level.model';

export interface IRating {
  id?: number;
  score?: number;
  level?: RatingLevel;
  ratedAt?: string;
  resource?: IResource | null;
  objective?: IObjective | null;
}

export const defaultValue: Readonly<IRating> = {};
