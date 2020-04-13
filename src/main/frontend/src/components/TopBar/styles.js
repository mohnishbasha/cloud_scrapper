import { makeStyles } from '@material-ui/core';
import * as V from '../../layouts/variables';

export default makeStyles((theme) => ({
  root: {
    borderBottom: `1px solid ${theme.palette.background.default}`,
    boxShadow: `0 0 35px 0  ${theme.palette.background.default}`,
    backgroundColor: V.mainColor,
    display: 'flex',
    alignItems: 'center',
    height: 61,
  },
  toolbar: {
    minHeight: 'auto',
    width: '100%',
    paddingLeft: 0,
  },
  brandWrapper: {
    background: V.mainColor,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    width: '400px',
    height: V.topHeight,
    flexShrink: 0,
  },
  logo: {
    width: 'calc(100% - 90px)',
    maxWidth: '100%',
    margin: 'auto',
  },
  logoImg: {
    width: '100%',
  },
  signOutButton: {
    marginLeft: theme.spacing(1),
  },
  hideToggleButton: {
    display: 'none',
  },
  flexGrow: {
    flexGrow: 1,
  },
}));
