import React from 'react';
import PropTypes from 'prop-types';
import { Toolbar, IconButton, AppBar } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import InputIcon from '@material-ui/icons/Input';
import API from '../../providers/API';
import useStyles from './styles';

const TopBar = (props) => {
  const { onToggleSidebar } = props;
  const classes = useStyles(props);
  const logout = () => {
    API.logout();
  };

  return (
    <AppBar className={classes.root}>
      <Toolbar className={classes.toolbar}>
        <div className={classes.brandWrapper}>
          <IconButton aria-label="Menu" onClick={onToggleSidebar}>
            <MenuIcon />
          </IconButton>
        </div>

        <div className={classes.flexGrow} />
        <IconButton className={classes.signOutButton} onClick={logout}>
          <InputIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
};

TopBar.propTypes = {
  onToggleSidebar: PropTypes.func.isRequired,
};

export default TopBar;
