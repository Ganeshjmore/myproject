import dayjs from 'dayjs';
import { IResource } from 'app/shared/model/resource.model';

export interface IGitlabContributionMatrix {
  id?: number;
  totalCommits?: number;
  mergeRequests?: number;
  issuesClosed?: number;
  codeReviews?: number;
  lastUpdated?: string;
  resource?: IResource | null;
}

export const defaultValue: Readonly<IGitlabContributionMatrix> = {};
