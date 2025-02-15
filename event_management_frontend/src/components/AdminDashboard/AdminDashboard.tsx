import React, { useState } from "react";
import styles from "./AdminDashboard.module.css";
import { FiFilter } from "react-icons/fi";

interface Event {
  id: number;
  name: string;
  description: string;
  slotsFilled: string;
  checked: boolean;
}

interface Volunteer {
  id: number;
  name: string;
  preferences: string;
  availability: string;
  status: string;
  checked: boolean;
}

export function AdminDashboard() {
  const [activeTab, setActiveTab] = useState("events"); // Track active tab
  const [showFilterPopup, setShowFilterPopup] = useState(false); // Popup state
  const [selectAll, setSelectAll] = useState(false); // State for select all checkbox

  {/*INFO for tables*/ }
  {/*Events*/}
  const [events, setEvents] = useState<Event[]>([
    { id: 1, name: "Event1", description: "small description", slotsFilled: "0/6", checked: false },
    { id: 2, name: "Event2", description: "small description", slotsFilled: "0/6", checked: false },
    { id: 3, name: "Event3 ", description: "small description", slotsFilled: "0/6", checked: false },
  ]);

  {/*Volunteers*/}
  const [volunteers, setVolunteers] = useState<Volunteer[]>([
    { id: 1, name: "John Doe", preferences: "Helper", availability: "mon", status: "Active", checked: false },
    { id: 2, name: "Jane Smith", preferences: "Coordinator", availability: "mon", status: "Pending", checked: false },
  ]);

  const handleSelectAllChange = () => {
    const newSelectAll = !selectAll;
    setSelectAll(newSelectAll);
    setEvents(events.map(event => ({ ...event, checked: newSelectAll })));
    setVolunteers(volunteers.map(volunteer => ({ ...volunteer, checked: newSelectAll })));
  };

  const handleEventCheckboxChange = (id: number) => {
    setEvents(events.map(event => event.id === id ? { ...event, checked: !event.checked } : event));
    setVolunteers(volunteers.map(volunteer => volunteer.id === id ? { ...volunteer, checked: !volunteer.checked } : volunteer));
  };

  const resetCheckboxes = () => {
    setSelectAll(false);
    setEvents(events.map(event => ({ ...event, checked: false })));
    setVolunteers(volunteers.map(volunteer => ({ ...volunteer, checked: false })));
  };

  const handleTabChange = (tab: string) => {
    resetCheckboxes();
    setActiveTab(tab);
  };

  return (
    <div className={styles.adminDashboard}>
      {/* Navigation Bar */}
      <div className={styles.navbar}>
        <span
          className={activeTab === "events" ? styles.activeTab : styles.inactiveTab}
          onClick={() => handleTabChange("events")}
        >
          Events
        </span>
        <span
          className={activeTab === "volunteers" ? styles.activeTab : styles.inactiveTab}
          onClick={() => handleTabChange("volunteers")}
        >
          Volunteers
        </span>
      </div>

      {/*Filter and new event button*/}
      <div className={styles.header}>
        <button className={styles.filterButton} onClick={() => setShowFilterPopup(true)}>
          <FiFilter size={20} />
        </button>
        <button className={styles.newEventButton}>
          {activeTab === "events" ? "New Event +" : "Add Volunteer +"}
        </button>
      </div>

      {/* Filter Popup */}
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

      {/* Shows tables depending on which tab is selected*/}
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
                    checked={event.checked}
                    onChange={() => handleEventCheckboxChange(event.id)}
                  />
                </td>
                <td>{event.name}</td>
                <td>{event.description}</td>
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
                <input type="checkbox" checked={selectAll} onChange={handleSelectAllChange} />
              </th>
              <th>Volunteer Name</th>
              <th>Preferences</th>
              <th>Availability</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {volunteers.map((volunteer) => (
              <tr key={volunteer.id}>
                <td>
                  <input
                    type="checkbox"
                    checked={volunteer.checked}
                    onChange={() => handleEventCheckboxChange(volunteer.id)}
                  />
                </td>
                <td>{volunteer.name}</td>
                <td>{volunteer.preferences}</td>
                <td>{volunteer.availability}</td>
                <td>{volunteer.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
