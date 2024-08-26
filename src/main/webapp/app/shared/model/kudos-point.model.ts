import { IResource } from 'app/shared/model/resource.model';

export interface IKudosPoint {
  id?: number;
  givenBy?: string;
  comments?: string | null;
  points?: number;
  category?: string;
  resource?: IResource | null;
}

export const defaultValue: Readonly<IKudosPoint> = {};
