import React, { useState } from 'react';
import clsx from 'clsx';
import { Switch, Route } from 'react-router-dom';
import { createMuiTheme } from '@material-ui/core/styles';
import { ThemeProvider } from '@material-ui/styles';
import { Sidebar } from '../components';
import useStyles from './mainStyles';
import ScrapeView from '../scrape/ScrapeView';
import EC2View from '../ec2/EC2View';
import Dashboard from '../dashboard/Dashboard';
import * as V from './variables';

const THEME = createMuiTheme({
  typography: {
    fontSize: 12,
    fontWeightLight: 300,
    fontWeightRegular: 400,
    fontWeightMedium: 500,
  },
  palette: {
    primary: {
      main: V.mainColor,
    },
  },
  overrides: {
    MuiTableCell: {
      root: {
        padding: 8,
      },
    },
    MuiExpansionPanelSummary: {
      root: {
        '&$expanded': {
          minHeight: 40,
        },
      },
    },
  },
});

const Main = () => {
  const classes = useStyles();
  const [openSidebar, setOpenSidebar] = useState(true);

  const handleToggleSidebar = () => {
    setOpenSidebar(!openSidebar);
  };

  return (
    <ThemeProvider theme={THEME}>
      <Sidebar openSidebar={openSidebar} onToggleSidebar={handleToggleSidebar} />
      <main className={clsx({
        [classes.contentShift]: openSidebar,
        [classes.content]: true,
      })}
      >

        <Switch>
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/scrape" component={ScrapeView} />
          {/* <Route path="/ec2" component={EC2View} />
          <Route path="/scrape/:id/ec2" component={ScrapeResourceView} /> */}
          <Route path="/scrape/:id/ec2" component={EC2View} />
        </Switch>
      </main>
    </ThemeProvider>
  );
};

export default Main;
