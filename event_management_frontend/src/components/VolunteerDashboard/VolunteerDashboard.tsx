import React, { useState } from "react";
import styles from "././VolunteerDashboard.module.css";
import { FiFilter } from "react-icons/fi";

export function VolunteerDashboard() {
  const [activeTab, setActiveTab] = useState("events"); // Track active tab
  const [showFilterPopup, setShowFilterPopup] = useState(false); // Popup state
  {/*INFO for tables*/ }
  {/*Events*/}
  const [events] = useState([
    { id: 1, name: "Event1", description: "small description", slotsFilled: "0/6" },
    { id: 2, name: "Event2", description: "small description", slotsFilled: "0/6" },
    { id: 3, name: "Event3 ", description: "small description", slotsFilled: "0/6" },
  ]);

  return (
    <div className={styles.volunteerDashboard}>
      {/* Navigation Bar */}
      <div className={styles.navbar}>
        <span
          className={activeTab === "events" ? styles.activeTab : styles.inactiveTab}
          onClick={() => setActiveTab("events")}
        >
          Events Available
        </span>
      </div>

      {/*Filter and new event button*/}
      <div className={styles.header}>
        <button className={styles.filterButton} onClick={() => setShowFilterPopup(true)}>
          <FiFilter size={20} />
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
                <input type="checkbox" />
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
                  <input type="checkbox" />
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
