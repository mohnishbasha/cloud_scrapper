/* eslint-disable no-unused-vars */
import React from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';
import {
  Drawer, List, ListItem, ListItemIcon, ListItemText, IconButton,
} from '@material-ui/core';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import MenuIcon from '@material-ui/icons/Menu';
import pages from './pages';
import useStyles from './styles';
import TopBar from '../TopBar/TopBar';

const Sidebar = (props) => {
  const { openSidebar, onToggleSidebar } = props;
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <TopBar openSidebar={openSidebar} onToggleSidebar={onToggleSidebar} />
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={openSidebar}
        classes={{
          paper: classes.drawerPaper,
        }}
        PaperProps={{ elevation: 10 }}
      >

        <List component="div">
          {pages.map((page) => (
            <ListItem
              key={page.title}
              activeClassName={classes.activeListItem}
              className={classes.listItem}
              component={NavLink}
              to={page.href}
              button
            >
              <ListItemIcon className={classes.listItemIcon}>
                {page.icon}
              </ListItemIcon>
              <ListItemText primary={page.title} />
            </ListItem>
          ))}
        </List>
      </Drawer>
    </div>
  );
};
Sidebar.propTypes = {
  openSidebar: PropTypes.bool.isRequired,
  onToggleSidebar: PropTypes.func.isRequired,
};

export default Sidebar;
