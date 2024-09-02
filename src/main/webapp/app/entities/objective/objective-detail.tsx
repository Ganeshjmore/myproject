import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './objective.reducer';

export const ObjectiveDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const objectiveEntity = useAppSelector(state => state.objective.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objectiveDetailsHeading">Objective</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{objectiveEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{objectiveEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{objectiveEntity.description}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {objectiveEntity.createdAt ? <TextFormat value={objectiveEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{objectiveEntity.status}</dd>
          <dt>Resource</dt>
          <dd>{objectiveEntity.resource ? objectiveEntity.resource.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/objective" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/objective/${objectiveEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObjectiveDetail;
