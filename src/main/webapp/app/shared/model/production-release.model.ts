import dayjs from 'dayjs';
import { IResource } from 'app/shared/model/resource.model';

export interface IProductionRelease {
  id?: number;
  title?: string;
  description?: string;
  releaseDate?: string;
  application?: string;
  resource?: IResource | null;
}

export const defaultValue: Readonly<IProductionRelease> = {};
