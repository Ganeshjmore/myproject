import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const HRIS = () => {
  return (
    <>
     <MenuItem icon="pen" to="/manager">
        Manager
      </MenuItem>
      <MenuItem icon="asterisk" to="/resource">
        Resource
      </MenuItem> 
    </>
  );
};

export default HRIS as React.ComponentType<any>;
