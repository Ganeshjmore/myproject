import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      {/* <MenuItem icon="asterisk" to="/manager">
        Manager
      </MenuItem>
      <MenuItem icon="asterisk" to="/resource">
        Resource
      </MenuItem> */}
      <MenuItem icon="asterisk" to="/objective">
        Objective
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/rating">
        Rating
      </MenuItem>
      <MenuItem icon="asterisk" to="/production-release">
        Production Release
      </MenuItem>
      <MenuItem icon="asterisk" to="/kudos-point">
        Kudos Point
      </MenuItem>
      <MenuItem icon="asterisk" to="/gitlab-contribution-matrix">
        Gitlab Contribution Matrix
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
