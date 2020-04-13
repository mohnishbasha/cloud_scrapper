import React from 'react';
import DashboardIcon from '@material-ui/icons/Dashboard';
import Grain from '@material-ui/icons/Grain';

const pages = [
  {
    title: 'Dashboard',
    href: '/dashboard',
    icon: <DashboardIcon />,
  },
  {
    title: 'Scrape',
    href: '/scrape',
    icon: <Grain />,
  },
];

export default pages;
