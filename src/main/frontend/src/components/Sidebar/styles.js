import { makeStyles } from '@material-ui/core';
import * as V from '../../layouts/variables';

export default makeStyles((theme) => ({

  root: {
    display: 'flex',
    paddingTop: theme.spacing(5),
  },
  drawer: {
    width: V.drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: V.drawerWidth,
    top: V.topHeight,
    boxShadow: '12px 4px 16px 2px rgba(65, 64, 70, 0.3)',
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(1, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  listItemIcon: {
    color: '#37474f',
    alignItems: 'center',
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2),
    minWidth: theme.spacing(2),
  },
  listItem: {
    color: '#37474f',
  },
  activeListItem: {
    backgroundColor: V.mainColor,
  },
  large: {
    margin: theme.spacing(3),
    width: theme.spacing(10),
    height: theme.spacing(10),
    alignSelf: 'center',
  },
  dividerRoot: {
    margin: theme.spacing(2, 0, 2, 0),
  },
}));
