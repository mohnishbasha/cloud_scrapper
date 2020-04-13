import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import DescriptionIcon from '@material-ui/icons/Description';
import { makeStyles } from '@material-ui/styles';
import {
  Grid, Box, Tabs, Tab,
} from '@material-ui/core';
import { CSVLink } from 'react-csv';
import EC2Provider from '../providers/EC2Provider';
import ScrapeProvider from '../providers/ScrapeProvider';
import EC2Table from './EC2Table';
import * as V from '../layouts/variables';
import { Expandable } from '../components';
import ScrapeResourceView from '../scrape/ScrapeResourceView';

const useStyles = makeStyles(() => ({
  fileIcon: {
    display: 'flex',
    alignItems: 'center',
    color: V.mainColor,
  },
}));

const EC2View = (props) => {
  const { match } = props;
  const [ec2s, setEc2s] = useState([]);
  const [tab, setTab] = useState(0);
  const classes = useStyles();
  const api = EC2Provider;
  const scrapeApi = ScrapeProvider;

  useEffect(() => {
    const apiPromise = scrapeApi.getEC2ByScrape(match.params.id);
    apiPromise.then(
      (resp) => {
        setEc2s(resp.data.data);
      },
    );
  }, []);

  const handleTabChange = (event, newValue) => {
    setTab(newValue);
  };


  return (
    <div className="wrapper">

      <Tabs value={tab} onChange={handleTabChange} centered indicatorColor="primary">
        <Tab label="Table" />
        <Tab label="Visualization" />
      </Tabs>

      { tab === 0
        ? (
          <Expandable>
            <Grid container direction="row" justify="flex-end">
              {
          ec2s.length > 0 ? (
            <CSVLink data={ec2s}>
              <DescriptionIcon variant="contained" color="primary" className={classes.fileIcon} />
            </CSVLink>
          ) : ''

          }
            </Grid>
            <Box p={1.5} />
            <EC2Table ec2s={ec2s} />
          </Expandable>
        )
        : <ScrapeResourceView ec2Data={ec2s} />}
    </div>
  );
};

EC2View.propTypes = {
  match: PropTypes.shape({ params: PropTypes.objectOf.isRequired }).isRequired,
};

export default EC2View;
