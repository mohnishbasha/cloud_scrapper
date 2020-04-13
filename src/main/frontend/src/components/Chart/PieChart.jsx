import React, { Component, useState, useEffect } from "react";
import { Pie } from 'react-chartjs-2';

const options = {
    responsive: false,
    legend: {
        position: 'left',
        labels: {
            boxWidth: 10
        }
    }
}

const DEFAULT_HEIGHT = 400;

const PieChart = props => {
    const [chartData, setChartData] = useState([]);

    const prepareChartData = {
        labels: Object.keys(props.data),
        datasets: [{
            label: "Pie dataset",
            backgroundColor: [
                "#F7464A",
                "#46BFBD",
                "#FDB45C",
                "#949FB1",
                "#4D5360",
            ],
            data: Object.values(props.data),
        }]
    }

    return (
        <div>
            <Pie data={prepareChartData} options={options} height={props.height || DEFAULT_HEIGHT} />
        </div>
    );

}

export default PieChart;

