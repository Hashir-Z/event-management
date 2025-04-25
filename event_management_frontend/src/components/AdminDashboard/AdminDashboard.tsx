import React, { useState, useEffect } from "react";
import styles from "./AdminDashboard.module.css";
import { FiFilter } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import axios from "axios";

interface Event {
  id: number;
  eventName: string;
  eventDescription: string;
  location: string;
  skills: string[];
  urgency: string;
  eventDate: string;
  slotsFilled: number;
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
  const [selectAllEvents, setSelectAllEvents] = useState(false); // State for select all events checkbox
  const [selectAllVolunteers, setSelectAllVolunteers] = useState(false); // State for select all volunteers checkbox

  // Events state
  const [events, setEvents] = useState<Event[]>([]);

  // Volunteers state
  const [volunteers, setVolunteers] = useState<Volunteer[]>([
    {
      id: 1,
      name: "John Doe",
      preferences: "Helper",
      availability: "mon",
      status: "Active",
      checked: false,
    },
    {
      id: 2,
      name: "Jane Smith",
      preferences: "Coordinator",
      availability: "mon",
      status: "Pending",
      checked: false,
    },
  ]);

  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/events")
      .then((response) => {
        console.log("Fetched raw data:", response.data);

        if (!Array.isArray(response.data)) {
          console.error("Error: Expected an array but got:", response.data);
          return;
        }

        const formattedEvents = response.data.map((event: any) => ({
          id: event.id,
          eventName: event.eventName || event.name, // Handle eventName
          eventDescription: event.eventDescription || event.description, // Handle eventDescription
          location: event.location, // Handle location
          skills: event.skills || [], // Ensure skills is an array (or an empty array if missing)
          urgency: event.urgency, // Handle urgency
          eventDate: event.eventDate, // Handle eventDate
          slotsFilled: event.slotsFilled || 0, // Handle slotsFilled, default to 0 if undefined
          checked: false, // Initially false
        }));

        console.log("Formatted events:", formattedEvents);
        setEvents(formattedEvents);
      })
      .catch((error) => console.error("Error fetching events:", error));
  }, []);

  const handleDeleteEvent = (id: number) => {
    axios
      .delete(`http://localhost:8080/api/events/${id}`)
      .then(() => {
        setEvents(events.filter((event) => event.id !== id)); // Remove deleted event from state
      })
      .catch((error) => {
        console.error("Error deleting event:", error);
      });
  };

  const handleEventUpdateClick = (event: Event) => {
    navigate(`/EditEvent/${event.id}`); // Navigate to the edit form with event ID
  };

  const handleSelectAllChange = () => {
    if (activeTab === "events") {
      const newSelectAllEvents = !selectAllEvents;
      setSelectAllEvents(newSelectAllEvents);
      setEvents(
        events.map((event) => ({ ...event, checked: newSelectAllEvents }))
      );
    } else if (activeTab === "volunteers") {
      const newSelectAllVolunteers = !selectAllVolunteers;
      setSelectAllVolunteers(newSelectAllVolunteers);
      setVolunteers(
        volunteers.map((volunteer) => ({
          ...volunteer,
          checked: newSelectAllVolunteers,
        }))
      );
    }
  };

  const handleEventCheckboxChange = (id: number, type: string) => {
    if (type === "event") {
      setEvents(
        events.map((event) =>
          event.id === id ? { ...event, checked: !event.checked } : event
        )
      );
    } else if (type === "volunteer") {
      setVolunteers(
        volunteers.map((volunteer) =>
          volunteer.id === id
            ? { ...volunteer, checked: !volunteer.checked }
            : volunteer
        )
      );
    }
  };

  const resetCheckboxes = () => {
    setSelectAllEvents(false);
    setSelectAllVolunteers(false);
    setEvents(events.map((event) => ({ ...event, checked: false })));
    setVolunteers(
      volunteers.map((volunteer) => ({ ...volunteer, checked: false }))
    );
  };

  const handleTabChange = (tab: string) => {
    resetCheckboxes();
    setActiveTab(tab);
  };

  // Separate handlers for adding event and volunteer
  const handleEventAddClick = () => {
    navigate("/EventForm");
  };

  const handleVolunteerAddClick = () => {
    navigate("/VolunteerDetailsForm");
  };

  return (
    <div className={styles.adminDashboard}>
      {/* Navigation Bar */}
      <div className={styles.navbar}>
        <span
          className={
            activeTab === "events" ? styles.activeTab : styles.inactiveTab
          }
          onClick={() => handleTabChange("events")}
        >
          Events
        </span>
        <span
          className={
            activeTab === "volunteers" ? styles.activeTab : styles.inactiveTab
          }
          onClick={() => handleTabChange("volunteers")}
        >
          Volunteers
        </span>
      </div>

      {/* Filter and new event/volunteer button */}
      <div className={styles.header}>
        <button
          className={styles.filterButton}
          onClick={() => setShowFilterPopup(true)}
        >
          <FiFilter size={20} />
        </button>
        <button
          className={styles.newEventButton}
          onClick={
            activeTab === "events"
              ? handleEventAddClick
              : handleVolunteerAddClick
          }
        >
          {activeTab === "events" ? "New Event +" : "Add Volunteer +"}
        </button>
      </div>

      {/* Filter Popup */}
      {showFilterPopup && (
        <div
          className={styles.popupOverlay}
          onClick={() => setShowFilterPopup(false)}
        >
          <div className={styles.popup} onClick={(e) => e.stopPropagation()}>
            <h3>Filter Options</h3>
            <p>Bla Bla Bla filters.</p>
            <button
              className={styles.closeButton}
              onClick={() => setShowFilterPopup(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}

      {/* Shows tables depending on which tab is selected */}
      {activeTab === "events" ? (
        <table className={styles.eventTable}>
          <thead>
            <tr>
              <th>Select</th>
              <th>Event Name</th>
              <th>Description</th>
              <th>Slots Filled</th>
              <th>Actions</th> {/* New column for delete button */}
            </tr>
          </thead>
          <tbody>
            {events.map((event) => (
              <tr key={event.id}>
                <td>
                  <input
                    type="checkbox"
                    checked={event.checked}
                    onChange={() =>
                      handleEventCheckboxChange(event.id, "event")
                    }
                  />
                </td>
                <td>{event.eventName}</td>
                <td>{event.eventDescription}</td>
                <td>{event.slotsFilled}</td>
                <td>
                  <button onClick={() => handleEventUpdateClick(event)}>
                    Update
                  </button>{" "}
                  {/* Update button */}
                  <button onClick={() => handleDeleteEvent(event.id)}>
                    Delete
                  </button>{" "}
                  {/* Delete button */}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <table className={styles.eventTable}>
          <thead>
            <tr>
              <th>
                <input
                  type="checkbox"
                  checked={selectAllVolunteers}
                  onChange={handleSelectAllChange}
                />
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
                    onChange={() =>
                      handleEventCheckboxChange(volunteer.id, "volunteer")
                    }
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
