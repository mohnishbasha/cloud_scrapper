import React from 'react';
import PropTypes from 'prop-types';
import { Box } from '@material-ui/core';
import _ from 'lodash';
import isArray from 'lodash/isArray';
import HexagonGrid from './HexGrid';

const HexMap = (props) => {
  const {
    level,
    title,
    tree,

  } = props;

  const hasChildren = (_tree) => _tree !== undefined && Object.keys(_tree) !== undefined && !isArray(_tree);

  return (
    <>
      <Box justifyContent="center" flexDirection="column" display="flex">
        <h4 align="center">{title}</h4>
      </Box>
      {
          hasChildren(tree)
            ? Object.keys(tree).map((key) => <HexMap tree={tree[key]} title={key} level={level + 1} />)
            : (
              <Box flexDirection="column" display="flex">
                <HexagonGrid gridWidth={200} gridHeight={200} hexagons={tree} />
              </Box>
            )
          }

    </>
  );
};

HexMap.propTypes = {
  level: PropTypes.number,
  title: PropTypes.string.isRequired,
  tree: PropTypes.oneOfType([PropTypes.object, PropTypes.arrayOf]).isRequired,
};

HexMap.defaultProps = {
  level: 1,
};

export default HexMap;
