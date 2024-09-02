import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
     <MenuItem icon="asterisk" to="/production-release">
        Production Release
      </MenuItem>
      <MenuItem icon="asterisk" to="/kudos-point">
        Kudos Point
      </MenuItem>
      <MenuItem icon="asterisk" to="/gitlab-contribution-matrix">
        Gitlab Contribution Matrix
      </MenuItem>
    
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
