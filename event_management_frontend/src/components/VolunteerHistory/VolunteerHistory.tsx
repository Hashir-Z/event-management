import React,{useState} from 'react';

import styles from './VolunteerHistory.module.css';
/*
export interface VolunteerHistoryProps {
  prop?: string;
}
*/
export function VolunteerHistory() {
  const [events, setEvents] = useState([
    {eventID: 1, eventName: "Event1", eventDescription: "Description1", location: "Location1", requiredSkills: ["Skill1","Skill2", "Skill3"], urgency: "Level1", eventDate: "Date1"},
    {eventID: 1, eventName: "Event2", eventDescription: "Description2", location: "Location2", requiredSkills: ["Skill1","Skill2", "Skill3"], urgency: "Level2", eventDate: "Date2"},
    {eventID: 1, eventName: "Event3", eventDescription: "Description3", location: "Location3", requiredSkills: ["Skill1","Skill2", "Skill3"], urgency: "Level3", eventDate: "Date3"}
  ]);



  return( 
    <div className={styles.VolunteerHistory}>
      <div className={styles.title}> Volunteer History </div>
      <div className={styles.VolunteerHisDis}> 
        <table>
          <thead className={styles.VolunteerHistoryInfo}>
            <tr>
              <th>Event Name</th>
              <th>Event Description</th>
              <th>Location</th>
              <th>Required Skills</th>
              <th>Urgency</th>
              <th>Event Date</th>
            </tr>
          </thead>
          <tbody className ={styles.VolunteerHistoryTable}>
            {events.map((event, key)=> (
                  <tr key ={key}>
                    <td>{event.eventName}</td>
                    <td>{event.eventDescription}</td>
                    <td>{event.location}</td>
                    <td>{event.requiredSkills.map(skill=>(
                      skill+="  "
                    ))}
                    </td>
                    <td>{event.urgency}</td>
                    <td>{event.eventDate}</td>
                  </tr>
            ))}
          </tbody>
          <tfoot>

          </tfoot>
        </table>
      </div>
  
    </div>);
}
