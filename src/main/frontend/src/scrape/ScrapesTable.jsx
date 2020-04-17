import React, { useState } from 'react';
import PropTypes from 'prop-types';
import moment from 'moment';
import { makeStyles } from '@material-ui/styles';
import {
  Card,
  CardActions,
  CardContent,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TablePagination,
} from '@material-ui/core';
import { NavLink } from 'react-router-dom';

const useStyles = makeStyles(() => ({
  root: {},
  content: {
    padding: 0,
  },
  nameContainer: {
    display: 'flex',
    alignItems: 'center',
  },
  avatar: {
    marginRight: '20px',
  },
  actions: {
    justifyContent: 'flex-end',
  },
}));

const ScrapesTable = (props) => {
  const { scrapes } = props;

  const classes = useStyles();

  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [page, setPage] = useState(0);

  const handlePageChange = (event, pageN) => {
    setPage(pageN);
  };

  const handleRowsPerPageChange = (event) => {
    setRowsPerPage(event.target.value);
  };


  return (
    <Card className={classes.root}>
      <CardContent className={classes.content}>
        <div>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell>Resource</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Run date</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {
                // eslint-disable-next-line max-len
                scrapes.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((scrape) => (
                  <TableRow className={classes.tableRow} hover key={scrape.id}>
                    <TableCell>
                      { scrape.status === 'COMPLETE' && <NavLink to={`/scrape/${scrape.id}/${scrape.resourceType}`}>{scrape.id}</NavLink> }
                      { scrape.status !== 'COMPLETE' && scrape.id }
                    </TableCell>
                    <TableCell>{scrape.resourceType}</TableCell>
                    <TableCell>{scrape.status}</TableCell>
                    <TableCell>
                      {moment(scrape.createdAt).format('DD/MM/YYYY')}
                    </TableCell>
                  </TableRow>
                ))
              }
            </TableBody>
          </Table>
        </div>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination component="div" count={scrapes.length} onChangePage={handlePageChange} onChangeRowsPerPage={handleRowsPerPageChange} page={page} rowsPerPage={rowsPerPage} rowsPerPageOptions={[5, 10, 25]} />
      </CardActions>
    </Card>
  );
};

ScrapesTable.propTypes = {
  scrapes: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default ScrapesTable;
