import React, { useEffect, useState} from 'react'
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {getEntities} from 'app/entities/resource/resource.reducer';
import ChartComponent from './ChartComponent';
import { getEntities as objectives } from 'app/entities/objective/objective.reducer';

export default function resourceSelection() {
  const dispatch = useAppDispatch();

  const [selectedResource, setSelectedResource] = useState("");
  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  useEffect(() => {
    dispatch(objectives({}));
    
  }, [selectedResource]);

  const resourceList = useAppSelector(state => state.resource.entities);
  const objective = useAppSelector(state => state.objective.entities);

  function onChangeResource (e){
    setSelectedResource(e.target.value)
   }

  return (
    <div>
   <table>
    <tr>
      <td>Resources</td>
      <td>
      <select id="resource-select" onChange={onChangeResource} style={{padding:'5px'}}>
      <option value=""style={{opacity:'50%'}} disabled selected>Select Resource</option>
      {resourceList && resourceList.length > 0 ? (
        resourceList.map((resource) => (
          <option key={resource.id} value={resource.id}>
            {resource.name}
          </option>
        ))
      ) : (
        <option value="">Loading...</option>
      )}
    </select>
      </td>
      <td style={{paddingLeft:'20px'}}>Year</td>
      <td>
       <select style={{padding:'5px'}}>
         <option selected>2023-2024</option>
         <option> 2022-2023</option>
         <option> 2021-2022</option>
       </select>
      </td>
    </tr>
   </table>
   {selectedResource !== "" && objective &&
    <ChartComponent title="Ratings Score" selectedResource = {selectedResource} objective = {objective}/>
      }

    </div>
  )
}
