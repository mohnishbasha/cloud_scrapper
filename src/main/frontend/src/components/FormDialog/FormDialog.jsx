import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import Select from '@material-ui/core/Select';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

const FormDialog = (props) => {
  const {
    open, title, onCancel, onSave, handleChange,
  } = props;

  return (
    <>
      <Dialog open={open} onClose={onCancel} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">{title}</DialogTitle>
        <DialogContent>
          <Select
            native
            onChange={handleChange}
          >
            <option value="ec2">EC2</option>
          </Select>
        </DialogContent>
        <DialogActions>
          <Button onClick={onCancel} color="primary">
            Cancel
          </Button>
          <Button onClick={onSave} color="primary">
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
FormDialog.propTypes = {
  open: PropTypes.bool.isRequired,
  title: PropTypes.string.isRequired,
  onCancel: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired,
  handleChange: PropTypes.func.isRequired,
};
export default FormDialog;
