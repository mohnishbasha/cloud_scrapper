import React from 'react';
import PropTypes from 'prop-types';
import { Bar } from 'react-chartjs-2';

const options = {
  elements: {
    rectangle: {
      borderWidth: 2,
      borderColor: 'rgb(0, 255, 0)',
      borderSkipped: 'bottom',
    },
  },
  scales: {
    yAxes: [{
      ticks: {
        beginAtZero: true,
      },
    }],
  },
  responsive: true,
  legend: {
    position: 'top',
  },
  maintainAspectRatio: false,
};

const DEFAULT_HEIGHT = 400;

const getRandomColor = (length) => {
  const colorArr = [];
  const letters = '0123456789ABCDEF'.split('');
  for (let index = 0; index < length; index += 1) {
    let color = '#';
    for (let i = 0; i < 6; i += 1) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    colorArr.push(color);
  }
  return colorArr;
};

const PieChart = (props) => {
  const { data, height } = props;
  const prepareChartData = {
    labels: Object.keys(data),
    datasets: [{
      label: 'Bar dataset',
      backgroundColor: getRandomColor(Object.keys(data).length),
      data: Object.values(data),
    }],
  };

  return (
    <div>
      <Bar data={prepareChartData} options={options} height={height || DEFAULT_HEIGHT} />
    </div>
  );
};

PieChart.propTypes = {
  data: PropTypes.node.isRequired,
  height: PropTypes.number,
};
PieChart.defaultProps = {
  height: DEFAULT_HEIGHT,
};
export default PieChart;
