import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const Performance = () => {
  return (
    <>
      <MenuItem icon="asterisk" to="/objective">
        Objective
      </MenuItem>
      <MenuItem icon="comment" to="/comment">
        Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/rating">
        Rating
      </MenuItem>
    </>
  );
};

export default Performance as React.ComponentType<any>;
