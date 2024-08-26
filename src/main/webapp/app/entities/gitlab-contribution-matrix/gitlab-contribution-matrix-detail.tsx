import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gitlab-contribution-matrix.reducer';

export const GitlabContributionMatrixDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const gitlabContributionMatrixEntity = useAppSelector(state => state.gitlabContributionMatrix.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gitlabContributionMatrixDetailsHeading">GitlabContributionMatrix</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gitlabContributionMatrixEntity.id}</dd>
          <dt>
            <span id="totalCommits">Total Commits</span>
          </dt>
          <dd>{gitlabContributionMatrixEntity.totalCommits}</dd>
          <dt>
            <span id="mergeRequests">Merge Requests</span>
          </dt>
          <dd>{gitlabContributionMatrixEntity.mergeRequests}</dd>
          <dt>
            <span id="issuesClosed">Issues Closed</span>
          </dt>
          <dd>{gitlabContributionMatrixEntity.issuesClosed}</dd>
          <dt>
            <span id="codeReviews">Code Reviews</span>
          </dt>
          <dd>{gitlabContributionMatrixEntity.codeReviews}</dd>
          <dt>
            <span id="lastUpdated">Last Updated</span>
          </dt>
          <dd>
            {gitlabContributionMatrixEntity.lastUpdated ? (
              <TextFormat value={gitlabContributionMatrixEntity.lastUpdated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Resource</dt>
          <dd>{gitlabContributionMatrixEntity.resource ? gitlabContributionMatrixEntity.resource.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gitlab-contribution-matrix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gitlab-contribution-matrix/${gitlabContributionMatrixEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GitlabContributionMatrixDetail;
