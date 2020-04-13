import { makeStyles } from '@material-ui/core';

const marginLeftSize = '220px';

export default makeStyles((theme) => ({
  root: {
    height: '100%',
    background: theme.palette.background.default,
    color: theme.palette.contrastText,
  },
  content: {
    padding: theme.spacing(2),
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  contentShift: {
    marginLeft: marginLeftSize,
  },
}));
