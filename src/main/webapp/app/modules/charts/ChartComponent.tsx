// ChartComponent.tsx
import React, { useEffect, useState } from 'react'
import ReactECharts from 'echarts-for-react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/rating/rating.reducer';
import { IRating } from 'app/shared/model/rating.model';
import { IObjective } from 'app/shared/model/objective.model';
// Define the type for chart options
interface ChartComponentProps {
    title: string,
    selectedResource: string,
    objective: IObjective
}


// Functional component
const ChartComponent: React.FC<ChartComponentProps> = ({ title, selectedResource, objective }) => {

    const dispatch = useAppDispatch();
    const ratings = useAppSelector(state => state.rating.entities);
   // const objectiv = Array.of(objective).filter(e => e.resource.id === parseInt(selectedResource));

    useEffect(() => {
        dispatch(getEntities({}));
    }, []);


    const data = {
        '2021-2022': [
            { objective: "Objective 1", score: 5 },
            { objective: "Objective 2", score: 5 },
            { objective: "Objective 3", score: 4 },
        ],
        '2022-2023': [
            { objective: "Objective 1", score: 3 },
            { objective: "Objective 2", score: 4 },
            { objective: "Objective 3", score: 5 },
        ],
        '2023-2024': [
            { objective: "Objective 1", score: 1 },
            { objective: "Objective 2", score: 3 },
            { objective: "Objective 3", score: 3 },
        ],
    };



    

    const years = Object.keys(data);
    const objectives = [...new Set(years.flatMap(year => data[year].map(item => item.objective)))];
    const seriesData = years.map(year => ({
        name: year,
        type: 'bar',
        data: objectives.map(obj => {
            const found = data[year].find(item => item.objective === obj);
            return found ? found.score : 0;
        })
    }));


    // Define the chart options
    const options = {
        title: {
            text: title,
        },
        tooltip: {},
        xAxis: {
            data: ['2021-2022', '2022-2023', '2023-2024'],
        },
        yAxis: {
            type: 'value',
            name: 'Score'},
        series:  seriesData

        };

    return <> <p>{ratings}</p><ReactECharts option={options} style={{ height: '200px', width: '60%' }} /></>;
};

export default ChartComponent;
