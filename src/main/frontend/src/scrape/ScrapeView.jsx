import React, { useState, useEffect } from 'react';
import { Button, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import AddIcon from '@material-ui/icons/Add';
import ScrapesTable from './ScrapesTable';
import { Expandable } from '../components';
import ScrapeProvider from '../providers/ScrapeProvider';
import * as V from '../layouts/variables';
import FormDialog from '../components/FormDialog/FormDialog';

const useStyles = makeStyles(() => ({
  add_icon: {
    backgroundColor: V.mainColor,
  },
}));

const ScrapeView = () => {
  const [scrapes, setScrapes] = useState([]);
  const [open, isOpen] = useState(false);
  const [resourceType, setResourceType] = useState('ec2');
  const api = ScrapeProvider;
  const classes = useStyles();

  const handleAddScrape = () => {
    const apiPromise = api.createScrape({ resource: resourceType });
    apiPromise.then(
      (resp) => {
        setScrapes(scrapes.concat(resp.data.data));
      },
    );
    isOpen(false);
  };

  const openAddScrape = () => {
    isOpen(true);
  };

  useEffect(() => {
    const apiPromise = api.getScrapes();
    apiPromise.then(
      (resp) => {
        setScrapes(resp.data.data);
      },
    );
  }, []);

  return (
    <div className="wrapper">
      <Expandable>
        <Button variant="contained" className={classes.add_icon} startIcon={<AddIcon />} onClick={openAddScrape}> Scrape </Button>
        <FormDialog title="Scrape" open={open} onCancel={() => isOpen(false)} onSave={handleAddScrape} handleChange={(e) => setResourceType(e.target.value)} />
        <Box p={1.5} />
        <ScrapesTable scrapes={scrapes} />
      </Expandable>
    </div>
  );
};
export default ScrapeView;
