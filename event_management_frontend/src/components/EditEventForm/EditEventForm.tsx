import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

interface Event {
  id: number;
  eventName: string;
  eventDescription: string;
  location: string;
  skills: string[];
  urgency: string;
  eventDate: string;
  slotsFilled: number;
}

export const EditEventForm = () => {
  const { eventId } = useParams();  // Get event ID from the URL
  const navigate = useNavigate();
  const [event, setEvent] = useState<Event | null>(null);

  // Fetch event details by ID on component mount
  useEffect(() => {
    axios.get(`http://localhost:8080/api/events/${eventId}`)
      .then((response) => {
        setEvent(response.data);  // Set event data to be edited
      })
      .catch((error) => {
        console.error('Error fetching event details:', error);
      });
  }, [eventId]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (event) {
      // Send PUT request to update the event
      axios.put(`http://localhost:8080/api/events/${event.id}`, event)
        .then(() => {
          // On success, navigate back to the admin dashboard
          navigate('/AdminDashboard');
        })
        .catch((error) => {
          console.error('Error updating event:', error);
        });
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (event) {
      // Update event state with the new value
      setEvent({
        ...event,
        [name]: value
      });
    }
  };

  return (
    <div>
      <h2>Edit Event</h2>
      {event ? (
        <form onSubmit={handleSubmit}>
          <div>
            <label>Event Name</label>
            <input
              type="text"
              name="eventName"
              value={event.eventName}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Description</label>
            <textarea
              name="eventDescription"
              value={event.eventDescription}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Location</label>
            <input
              type="text"
              name="location"
              value={event.location}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Skills</label>
            <input
              type="text"
              name="skills"
              value={event.skills.join(', ')}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Urgency</label>
            <input
              type="text"
              name="urgency"
              value={event.urgency}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Event Date</label>
            <input
              type="text"
              name="eventDate"
              value={event.eventDate}
              onChange={handleChange}
            />
          </div>
          <div>
            <label>Slots Filled</label>
            <input
              type="number"
              name="slotsFilled"
              value={event.slotsFilled}
              onChange={handleChange}
            />
          </div>
          <button type="submit">Update Event</button>
        </form>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};
