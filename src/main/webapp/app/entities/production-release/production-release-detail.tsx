import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './production-release.reducer';

export const ProductionReleaseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productionReleaseEntity = useAppSelector(state => state.productionRelease.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productionReleaseDetailsHeading">ProductionRelease</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productionReleaseEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{productionReleaseEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productionReleaseEntity.description}</dd>
          <dt>
            <span id="releaseDate">Release Date</span>
          </dt>
          <dd>
            {productionReleaseEntity.releaseDate ? (
              <TextFormat value={productionReleaseEntity.releaseDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="application">Application</span>
          </dt>
          <dd>{productionReleaseEntity.application}</dd>
          <dt>Resource</dt>
          <dd>{productionReleaseEntity.resource ? productionReleaseEntity.resource.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/production-release" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-release/${productionReleaseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductionReleaseDetail;
