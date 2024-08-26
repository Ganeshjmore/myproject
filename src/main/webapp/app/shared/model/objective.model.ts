import dayjs from 'dayjs';
import { IComment } from 'app/shared/model/comment.model';
import { IRating } from 'app/shared/model/rating.model';
import { IResource } from 'app/shared/model/resource.model';
import { ObjectiveStatus } from 'app/shared/model/enumerations/objective-status.model';

export interface IObjective {
  id?: number;
  title?: string;
  description?: string;
  createdAt?: string;
  status?: ObjectiveStatus;
  comments?: IComment[] | null;
  ratings?: IRating[] | null;
  resource?: IResource | null;
}

export const defaultValue: Readonly<IObjective> = {};
