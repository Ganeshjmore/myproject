import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating.reducer';

export const RatingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ratingEntity = useAppSelector(state => state.rating.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingDetailsHeading">Rating</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingEntity.id}</dd>
          <dt>
            <span id="score">Score</span>
          </dt>
          <dd>{ratingEntity.score}</dd>
          <dt>
            <span id="level">Level</span>
          </dt>
          <dd>{ratingEntity.level}</dd>
          <dt>
            <span id="ratedAt">Rated At</span>
          </dt>
          <dd>{ratingEntity.ratedAt ? <TextFormat value={ratingEntity.ratedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Resource</dt>
          <dd>{ratingEntity.resource ? ratingEntity.resource.id : ''}</dd>
          <dt>Objective</dt>
          <dd>{ratingEntity.objective ? ratingEntity.objective.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating/${ratingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingDetail;
