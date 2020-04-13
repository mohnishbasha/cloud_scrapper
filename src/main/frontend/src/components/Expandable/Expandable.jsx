import React from 'react';
import PropTypes from 'prop-types';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import useStyles from './styles';

const Expandable = (props) => {
  const classes = useStyles();
  const { children } = props;
  return (
    <ExpansionPanel defaultExpanded>
      <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1c-content" id="panel1c-header" />
      <ExpansionPanelDetails className={classes.details}>
        {children}
      </ExpansionPanelDetails>

    </ExpansionPanel>
  );
};
Expandable.propTypes = {
  children: PropTypes.node.isRequired,
};


export default Expandable;
