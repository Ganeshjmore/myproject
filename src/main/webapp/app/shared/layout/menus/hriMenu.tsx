import React from 'react';

import { NavDropdown } from './menu-components';
import HRIS from 'app/entities/hris';

export const HRI_Menu = props => (
  <NavDropdown icon="th-list" name="HRI" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <HRIS />
  </NavDropdown>
);
