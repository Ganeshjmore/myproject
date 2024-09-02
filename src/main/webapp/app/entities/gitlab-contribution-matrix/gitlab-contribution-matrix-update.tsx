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
import { IGitlabContributionMatrix } from 'app/shared/model/gitlab-contribution-matrix.model';
import { getEntity, updateEntity, createEntity, reset } from './gitlab-contribution-matrix.reducer';

export const GitlabContributionMatrixUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const resources = useAppSelector(state => state.resource.entities);
  const gitlabContributionMatrixEntity = useAppSelector(state => state.gitlabContributionMatrix.entity);
  const loading = useAppSelector(state => state.gitlabContributionMatrix.loading);
  const updating = useAppSelector(state => state.gitlabContributionMatrix.updating);
  const updateSuccess = useAppSelector(state => state.gitlabContributionMatrix.updateSuccess);
  const handleClose = () => {
    props.history.push('/gitlab-contribution-matrix' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResources({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.lastUpdated = convertDateTimeToServer(values.lastUpdated);

    const entity = {
      ...gitlabContributionMatrixEntity,
      ...values,
      resource: resources.find(it => it.id.toString() === values.resource.toString()),
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
          lastUpdated: displayDefaultDateTime(),
        }
      : {
          ...gitlabContributionMatrixEntity,
          lastUpdated: convertDateTimeFromServer(gitlabContributionMatrixEntity.lastUpdated),
          resource: gitlabContributionMatrixEntity?.resource?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="managerDashboardApp.gitlabContributionMatrix.home.createOrEditLabel"
            data-cy="GitlabContributionMatrixCreateUpdateHeading"
          >
            Create or edit a GitlabContributionMatrix
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="gitlab-contribution-matrix-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Total Commits"
                id="gitlab-contribution-matrix-totalCommits"
                name="totalCommits"
                data-cy="totalCommits"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Merge Requests"
                id="gitlab-contribution-matrix-mergeRequests"
                name="mergeRequests"
                data-cy="mergeRequests"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Issues Closed"
                id="gitlab-contribution-matrix-issuesClosed"
                name="issuesClosed"
                data-cy="issuesClosed"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Code Reviews"
                id="gitlab-contribution-matrix-codeReviews"
                name="codeReviews"
                data-cy="codeReviews"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Last Updated"
                id="gitlab-contribution-matrix-lastUpdated"
                name="lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="gitlab-contribution-matrix-resource" name="resource" data-cy="resource" label="Resource" type="select">
                <option value="" key="0" />
                {resources
                  ? resources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gitlab-contribution-matrix" replace color="info">
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

export default GitlabContributionMatrixUpdate;
