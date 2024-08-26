import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './gitlab-contribution-matrix.reducer';

export const GitlabContributionMatrixDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    setLoadModal(true);
  }, []);

  const gitlabContributionMatrixEntity = useAppSelector(state => state.gitlabContributionMatrix.entity);
  const updateSuccess = useAppSelector(state => state.gitlabContributionMatrix.updateSuccess);

  const handleClose = () => {
    props.history.push('/gitlab-contribution-matrix' + props.location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(gitlabContributionMatrixEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="gitlabContributionMatrixDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="managerDashboardApp.gitlabContributionMatrix.delete.question">
        Are you sure you want to delete this GitlabContributionMatrix?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-gitlabContributionMatrix" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default GitlabContributionMatrixDeleteDialog;
