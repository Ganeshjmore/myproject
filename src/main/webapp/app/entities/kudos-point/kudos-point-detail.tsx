import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kudos-point.reducer';

export const KudosPointDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const kudosPointEntity = useAppSelector(state => state.kudosPoint.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kudosPointDetailsHeading">KudosPoint</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kudosPointEntity.id}</dd>
          <dt>
            <span id="givenBy">Given By</span>
          </dt>
          <dd>{kudosPointEntity.givenBy}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{kudosPointEntity.comments}</dd>
          <dt>
            <span id="points">Points</span>
          </dt>
          <dd>{kudosPointEntity.points}</dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{kudosPointEntity.category}</dd>
          <dt>Resource</dt>
          <dd>{kudosPointEntity.resource ? kudosPointEntity.resource.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/kudos-point" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kudos-point/${kudosPointEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KudosPointDetail;
