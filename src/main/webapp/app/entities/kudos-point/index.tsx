import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KudosPoint from './kudos-point';
import KudosPointDetail from './kudos-point-detail';
import KudosPointUpdate from './kudos-point-update';
import KudosPointDeleteDialog from './kudos-point-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KudosPointUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KudosPointUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KudosPointDetail} />
      <ErrorBoundaryRoute path={match.url} component={KudosPoint} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KudosPointDeleteDialog} />
  </>
);

export default Routes;
