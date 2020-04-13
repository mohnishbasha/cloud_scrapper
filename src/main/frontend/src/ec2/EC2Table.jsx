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

const EC2Table = (props) => {
  const { ec2s } = props;

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
                <TableCell>Instance type</TableCell>
                <TableCell>Region</TableCell>
                <TableCell>State</TableCell>
                <TableCell>Launch time</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {
                // eslint-disable-next-line max-len
                ec2s.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((ec2) => (
                  <TableRow className={classes.tableRow} hover key={ec2.id}>
                    <TableCell>{ec2.id}</TableCell>
                    <TableCell>{ec2.instanceType}</TableCell>
                    <TableCell>{ec2.region}</TableCell>
                    <TableCell>{ec2.state}</TableCell>
                    <TableCell>
                      {moment(ec2.launchTime).format('DD/MM/YYYY hh:mm')}
                    </TableCell>
                  </TableRow>
                ))
              }
            </TableBody>
          </Table>
        </div>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination component="div" count={ec2s.length} onChangePage={handlePageChange} onChangeRowsPerPage={handleRowsPerPageChange} page={page} rowsPerPage={rowsPerPage} rowsPerPageOptions={[5, 10, 25]} />
      </CardActions>
    </Card>
  );
};

EC2Table.propTypes = {
  ec2s: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default EC2Table;
