import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResource } from 'app/shared/model/resource.model';
import { getEntities as getResources } from 'app/entities/resource/resource.reducer';
import { IObjective } from 'app/shared/model/objective.model';
import { getEntities as getObjectives } from 'app/entities/objective/objective.reducer';
import { IRating } from 'app/shared/model/rating.model';
import { RatingLevel } from 'app/shared/model/enumerations/rating-level.model';
import { getEntity, updateEntity, createEntity, reset } from './rating.reducer';

export const RatingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const resources = useAppSelector(state => state.resource.entities);
  const objectives = useAppSelector(state => state.objective.entities);
  const ratingEntity = useAppSelector(state => state.rating.entity);
  const loading = useAppSelector(state => state.rating.loading);
  const updating = useAppSelector(state => state.rating.updating);
  const updateSuccess = useAppSelector(state => state.rating.updateSuccess);
  const ratingLevelValues = Object.keys(RatingLevel);
  const handleClose = () => {
    props.history.push('/rating' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResources({}));
    dispatch(getObjectives({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.ratedAt = convertDateTimeToServer(values.ratedAt);

    const entity = {
      ...ratingEntity,
      ...values,
      resource: resources.find(it => it.id.toString() === values.resource.toString()),
      objective: objectives.find(it => it.id.toString() === values.objective.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          ratedAt: displayDefaultDateTime(),
        }
      : {
          level: 'LOW',
          ...ratingEntity,
          ratedAt: convertDateTimeFromServer(ratingEntity.ratedAt),
          resource: ratingEntity?.resource?.id,
          objective: ratingEntity?.objective?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="managerDashboardApp.rating.home.createOrEditLabel" data-cy="RatingCreateUpdateHeading">
            Create or edit a Rating
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="rating-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Score"
                id="rating-score"
                name="score"
                data-cy="score"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 1, message: 'This field should be at least 1.' },
                  max: { value: 5, message: 'This field cannot be more than 5.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Level" id="rating-level" name="level" data-cy="level" type="select">
                {ratingLevelValues.map(ratingLevel => (
                  <option value={ratingLevel} key={ratingLevel}>
                    {ratingLevel}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Rated At"
                id="rating-ratedAt"
                name="ratedAt"
                data-cy="ratedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="rating-resource" name="resource" data-cy="resource" label="Resource" type="select">
                <option value="" key="0" />
                {resources
                  ? resources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="rating-objective" name="objective" data-cy="objective" label="Objective" type="select">
                <option value="" key="0" />
                {objectives
                  ? objectives.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rating" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RatingUpdate;
