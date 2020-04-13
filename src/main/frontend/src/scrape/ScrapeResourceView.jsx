import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Box } from '@material-ui/core';
import _ from 'lodash';
import GroupChip from '../components/Chip/GroupChip';
import HexMap from '../components/HexGrid/HexMap';

const attributes = [
  { title: 'Region', key: 'region' },
  { title: 'Instance-type', key: 'instanceType' },
  { title: 'Instance-state', key: 'state' },
  { title: 'AWS-key', key: 'keyName' },
];


const ScrapeResourceView = (props) => {
  const { ec2Data } = props;
  const [filtered, setFiltered] = useState({});

  const onTagChange = (e, vals) => {
    const out = ec2Data.reduce((result, item) => {
      if (vals.length === 1) {
        const fst = result[item[vals[0].key]] = result[item[vals[0].key]] || [];
        fst.push(item);
      } else if (vals.length === 2) {
        const fst = result[item[vals[0].key]] = result[item[vals[0].key]] || {};
        const snd = fst[item[vals[1].key]] = fst[item[vals[1].key]] || [];
        snd.push(item);
      } else if (vals.length === 3) {
        const fst = result[item[vals[0].key]] = result[item[vals[0].key]] || {};
        const snd = fst[item[vals[1].key]] = fst[item[vals[1].key]] || {};
        const trd = snd[item[vals[2].key]] = snd[item[vals[2].key]] || [];
        trd.push(item);
      }
      return result;
    }, {});
    //  debugger;
    setFiltered(out);
    // console.log(out);
  };

  return (
    <>
      <Box p={1} />
      <GroupChip options={attributes} onTagChange={onTagChange} />
      <Box p={1.5} />
      {
       // eslint-disable-next-line max-len
       Object.keys(filtered).map((key) => (
         <>
           <Box border={1} p={1.5} flexDirection="column" display="flex">
             <HexMap title={key} tree={filtered[key]} />
           </Box>
           <Box p={1.5} />
         </>
       ))
      }

    </>
  );
};

ScrapeResourceView.propTypes = {
  // match: PropTypes.shape({ params: PropTypes.objectOf.isRequired }).isRequired,
  ec2Data: PropTypes.arrayOf(PropTypes.any).isRequired,
};

export default ScrapeResourceView;
