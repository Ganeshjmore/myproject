import { IObjective } from 'app/shared/model/objective.model';
import { IProductionRelease } from 'app/shared/model/production-release.model';
import { IKudosPoint } from 'app/shared/model/kudos-point.model';
import { IGitlabContributionMatrix } from 'app/shared/model/gitlab-contribution-matrix.model';
import { IManager } from 'app/shared/model/manager.model';

export interface IResource {
  id?: number;
  name?: string;
  email?: string;
  gpin?: string;
  role?: string;
  objectives?: IObjective[] | null;
  productionReleases?: IProductionRelease[] | null;
  kudosPoints?: IKudosPoint[] | null;
  gitlabContributions?: IGitlabContributionMatrix[] | null;
  manager?: IManager | null;
}

export const defaultValue: Readonly<IResource> = {};
