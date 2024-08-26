import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GitlabContributionMatrix from './gitlab-contribution-matrix';
import GitlabContributionMatrixDetail from './gitlab-contribution-matrix-detail';
import GitlabContributionMatrixUpdate from './gitlab-contribution-matrix-update';
import GitlabContributionMatrixDeleteDialog from './gitlab-contribution-matrix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GitlabContributionMatrixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GitlabContributionMatrixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GitlabContributionMatrixDetail} />
      <ErrorBoundaryRoute path={match.url} component={GitlabContributionMatrix} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GitlabContributionMatrixDeleteDialog} />
  </>
);

export default Routes;
