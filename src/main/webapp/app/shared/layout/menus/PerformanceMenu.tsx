import React from 'react';

import { NavDropdown } from './menu-components';
import Performance from 'app/entities/performance';

export const PerformanceMenu = props => (
  <NavDropdown icon="thumbs-up" name="Performances" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <Performance />
  </NavDropdown>
);
