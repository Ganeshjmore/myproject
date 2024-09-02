// ChartComponent.tsx
import React, { useEffect, useState} from 'react'
import ReactECharts from 'echarts-for-react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {getEntity} from 'app/entities/resource/resource.reducer';
// Define the type for chart options
interface ChartComponentProps {
  title: string,
  selectedResource: string
}


// Functional component
const ChartComponent: React.FC<ChartComponentProps> = ({ title, selectedResource }) => {

    const dispatch = useAppDispatch();
    const ratings = useAppSelector(state => state.rating.entities);

useEffect(() => {
    dispatch(getEntity(selectedResource));
   
  }, []);


  // Define the chart options
  const options = {
    title: {
      text: title + selectedResource,
    },
    tooltip: {},
    xAxis: {
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    },
    yAxis: {},
    series: [
      {
        name: 'Objective1',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20, 30],
      },
       {
          name: 'Objective2',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20, 30],
        },
        {
            name: 'Objective63',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20, 30],
          },
    ],
  };

  return <> <p>{ratings}</p><ReactECharts option={options} style={{ height: '200px', width: '60%' }} /></>;
};

export default ChartComponent;
