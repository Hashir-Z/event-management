
import React, { useState, useEffect } from "react";
import axios from 'axios';

import styles from './VolunteerHistory.module.css';

//export interface VolunteerHistoryProps {
  //prop?: string;
//}

interface VolunteerHistoryItem {
  eventId: number;
  eventName: string;
  eventDescription: string;
  location: string;
  requiredSkills: string[];
  urgency: string;
  eventDate: string;
  status: 'Confirmed' | 'Attended' | 'No_show';
}
export const VolunteerHistory = () => {
  const [volunteerHistory, setVolunteerHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);


  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/volunteer-history');
        setVolunteerHistory(response.data);
        console.log(response.data)
      } catch (err: any) {
        setError(err.message || 'Error loading volunteer history');
      }
    };
  
    fetchHistory();
  }, []);




  return( 
    <div className={styles.VolunteerHistoryContainer}>
      <div className={styles.title}> Volunteer History </div>
      <div className={styles.VolunteerHisDisContainer}> 
        <table>
          <thead className={styles.VolunteerHistoryInfo}>
            <tr>
            <th>Volunteer Name</th>
            <th>Event Name</th>
            <th>Event Description</th>
            <th>Event Location</th>
            <th>Required Skills</th>
            <th>Urgency</th>
            <th>Event Date</th>
            <th>Status</th>
            </tr>
          </thead>
          <tbody className ={styles.VolunteerHistoryTableBody}>
          {volunteerHistory.map((history) => (
            <tr key={`${history['eventId']}-${history['volunteerName']}`}> {/* Using composite key */}
              <td>{history['volunteerName']}</td>
              <td>{history['eventName']}</td>
              <td>{history['eventDescription']}</td>
              <td>{history['eventLocation']}</td>
              <td>{history['requiredSkills']}</td>
              <td>{history['urgency']}</td>
              <td>{new Date(history['eventDate']).toLocaleString()}</td>
              <td className={styles.statusTag}>{history['status']}</td>
            </tr>
          ))}
          </tbody>
          <tfoot>

          </tfoot>
        </table>
      </div>
  
    </div>);
}

export default VolunteerHistory; 

/*
// VolunteerHistory.tsx
import React, { useState } from 'react';
import styles from './VolunteerHistory.module.css';

// 1. Define type for event
interface Event {
  eventID: number;
  eventName: string;
  eventDescription: string;
  location: string;
  requiredSkills: string[];
  urgency: string;
  eventDate: string;
}

// 2. Component
export function VolunteerHistory() {
  const [events, setEvents] = useState<Event[]>([
    {
      eventID: 1,
      eventName: "Event1",
      eventDescription: "Description1",
      location: "Location1",
      requiredSkills: ["Skill1", "Skill2", "Skill3"],
      urgency: "High",
      eventDate: "2025-04-20",
    },
    {
      eventID: 2,
      eventName: "Event2",
      eventDescription: "Description2",
      location: "Location2",
      requiredSkills: ["Skill1", "Skill2"],
      urgency: "Medium",
      eventDate: "2025-05-10",
    },
    {
      eventID: 3,
      eventName: "Event3",
      eventDescription: "Description3",
      location: "Location3",
      requiredSkills: ["Skill1"],
      urgency: "Low",
      eventDate: "2025-06-01",
    },
  ]);

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Volunteer History</h2>

      <div className={styles.tableWrapper}>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>Event Name</th>
              <th>Description</th>
              <th>Location</th>
              <th>Required Skills</th>
              <th>Urgency</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {events.map((event) => (
              <tr key={event.eventID}>
                <td>{event.eventName}</td>
                <td>{event.eventDescription}</td>
                <td>{event.location}</td>
                <td>{event.requiredSkills.join(", ")}</td>
                <td>{event.urgency}</td>
                <td>{event.eventDate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
  */