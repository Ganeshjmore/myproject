import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Manager from './manager';
import Resource from './resource';
import Objective from './objective';
import Comment from './comment';
import Rating from './rating';
import ProductionRelease from './production-release';
import KudosPoint from './kudos-point';
import GitlabContributionMatrix from './gitlab-contribution-matrix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}manager`} component={Manager} />
        <ErrorBoundaryRoute path={`${match.url}resource`} component={Resource} />
        <ErrorBoundaryRoute path={`${match.url}objective`} component={Objective} />
        <ErrorBoundaryRoute path={`${match.url}comment`} component={Comment} />
        <ErrorBoundaryRoute path={`${match.url}rating`} component={Rating} />
        <ErrorBoundaryRoute path={`${match.url}production-release`} component={ProductionRelease} />
        <ErrorBoundaryRoute path={`${match.url}kudos-point`} component={KudosPoint} />
        <ErrorBoundaryRoute path={`${match.url}gitlab-contribution-matrix`} component={GitlabContributionMatrix} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
