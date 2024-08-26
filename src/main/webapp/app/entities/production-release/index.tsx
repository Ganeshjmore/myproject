import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionRelease from './production-release';
import ProductionReleaseDetail from './production-release-detail';
import ProductionReleaseUpdate from './production-release-update';
import ProductionReleaseDeleteDialog from './production-release-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionReleaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionReleaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionReleaseDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionRelease} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionReleaseDeleteDialog} />
  </>
);

export default Routes;
