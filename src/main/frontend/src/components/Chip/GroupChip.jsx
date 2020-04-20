import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { TextField } from '@material-ui/core';

const GroupText = (params, disabled) => (
  <TextField
    {...params}
    variant="outlined"
    placeholder="Group By"
    disabled={disabled}
  />
);

const GroupChip = (props) => {
  const [disabled] = useState(false);
  const { options, onTagChange } = props;

  return (
    <Autocomplete
      multiple
      id="tags-outlined"
      options={options}
      getOptionLabel={(option) => option.title}
      onChange={(e, val) => onTagChange(e, val)}
      renderInput={(params) => GroupText(params, disabled)}
    />
  );
};

GroupChip.propTypes = {
  options: PropTypes.arrayOf(PropTypes.any),
  onTagChange: PropTypes.func.isRequired,

};

GroupChip.defaultProps = {
  options: [],
};

export default GroupChip;
