import React, { useState, useEffect } from "react";
import styles from "./VolunteerDashboard.module.css";
import { FiFilter } from "react-icons/fi";
import axios from "axios";

interface EventData {
  id: number;
  eventName: string;
  eventDescription: string;
  slotsFilled: number;
  checked?: boolean;
}

export function VolunteerDashboard() {
  const [activeTab, setActiveTab] = useState("events");
  const [showFilterPopup, setShowFilterPopup] = useState(false);
  const [selectAll, setSelectAll] = useState(false);
  const [events, setEvents] = useState<EventData[]>([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/events")
        .then((response) => {
          const fetchedEvents = response.data.map((event: EventData) => ({
            ...event,
            checked: false,
            slotsFilled: `${event.slotsFilled}/6`, // Adjust as needed
          }));
          setEvents(fetchedEvents);
        })
        .catch((error) => {
          console.error("Error fetching events:", error);
        });
  }, []);

  const handleSelectAllChange = () => {
    const newSelectAll = !selectAll;
    setSelectAll(newSelectAll);
    setEvents(events.map(event => ({ ...event, checked: newSelectAll })));
  };

  const handleEventCheckboxChange = (id: number) => {
    setEvents(events.map(event =>
        event.id === id ? { ...event, checked: !event.checked } : event
    ));
  };

  return (
      <div className={styles.volunteerDashboard}>
        <div className={styles.navbar}>
        <span
            className={activeTab === "events" ? styles.activeTab : styles.inactiveTab}
            onClick={() => setActiveTab("events")}
        >
          Events Available
        </span>
        </div>

        <div className={styles.header}>
          <button className={styles.filterButton} onClick={() => setShowFilterPopup(true)}>
            <FiFilter size={20} />
          </button>
        </div>

        {showFilterPopup && (
            <div className={styles.popupOverlay} onClick={() => setShowFilterPopup(false)}>
              <div className={styles.popup} onClick={(e) => e.stopPropagation()}>
                <h3>Filter Options</h3>
                <p>Bla Bla Bla filters.</p>
                <button className={styles.closeButton} onClick={() => setShowFilterPopup(false)}>
                  Close
                </button>
              </div>
            </div>
        )}

        {activeTab === "events" ? (
            <table className={styles.eventTable}>
              <thead>
              <tr>
                <th>
                  <input type="checkbox" checked={selectAll} onChange={handleSelectAllChange} />
                </th>
                <th>Event Name</th>
                <th>Description</th>
                <th>Slots Filled</th>
              </tr>
              </thead>
              <tbody>
              {events.map((event) => (
                  <tr key={event.id}>
                    <td>
                      <input
                          type="checkbox"
                          checked={event.checked || false}
                          onChange={() => handleEventCheckboxChange(event.id)}
                      />
                    </td>
                    <td>{event.eventName}</td>
                    <td>{event.eventDescription}</td>
                    <td>{event.slotsFilled}</td>
                  </tr>
              ))}
              </tbody>
            </table>
        ) : (
            <table className={styles.eventTable}>
              <thead>
              <tr>
                <th>
                  <input type="checkbox" />
                </th>
                <th>Volunteer Name</th>
                <th>Preferences</th>
                <th>Availability</th>
                <th>Status</th>
              </tr>
              </thead>
            </table>
        )}
      </div>
  );
}
