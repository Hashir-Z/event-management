import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import Select from 'react-select';

interface SkillOption {
  value: string;
  label: string;
}

interface Event {
  id: number;
  eventName: string;
  eventDescription: string;
  location: string;
  skills: SkillOption[];
  urgency: string;
  eventDate: string;
  slotsFilled: number;
}

// Define skill options outside the component to prevent recreation on each render
const skillOptions: SkillOption[] = [
  { value: "JAVA", label: "Java" },
  { value: "PYTHON", label: "Python" },
  { value: "JAVASCRIPT", label: "JavaScript" },
  { value: "SQL", label: "SQL" },
  { value: "FRONTEND", label: "Frontend" },
  { value: "BACKEND", label: "Backend" },
  { value: "FULLSTACK", label: "Fullstack" }
];

export const EditEventForm = () => {
  const { eventId } = useParams<{ eventId: string }>();
  const navigate = useNavigate();
  const [event, setEvent] = useState<Event | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  // Fetch event details only once on component mount
  useEffect(() => {
    let isMounted = true;
    setLoading(true);
    setError(null);

    if (!eventId) {
      if (isMounted) {
        setError("No event ID provided");
        setLoading(false);
      }
      return;
    }

    axios.get(`http://localhost:8080/api/events/${eventId}`)
        .then((response) => {
          if (!isMounted) return;

          const eventData = response.data;

          // Convert backend skills (strings) to SkillOption objects for react-select
          const eventSkills = eventData.skills
              .map((skill: string) => skillOptions.find(option => option.value === skill))
              .filter(Boolean) as SkillOption[];

          setEvent({
            ...eventData,
            skills: eventSkills
          });
          setLoading(false);
        })
        .catch((error) => {
          if (!isMounted) return;
          console.error('Error fetching event details:', error);
          setError('Failed to load event details. Please try again later.');
          setLoading(false);
        });

    return () => {
      isMounted = false;
    };
  }, [eventId]); // Only depend on eventId, not skillOptions

  // Memoize handlers to prevent recreation on each render
  const handleSubmit = useCallback((e: React.FormEvent) => {
    e.preventDefault();

    if (!event) {
      setError("No event data to submit");
      return;
    }

    // Prevent multiple submissions
    if (isSubmitting) return;

    setIsSubmitting(true);

    // Create a copy of the event with skills formatted properly for the backend
    const updatedEvent = {
      ...event,
      skills: event.skills.map(skill => skill.value)
    };

    axios.put(`http://localhost:8080/api/events/${event.id}`, updatedEvent)
        .then(() => {
          navigate('/AdminDashboard');
        })
        .catch((error) => {
          console.error('Error updating event:', error);
          if (error.response) {
            setError(`Error: ${error.response.status} - ${error.response.data.message || 'Unknown error'}`);
          } else if (error.request) {
            setError('No response from server. Please check your connection and try again.');
          } else {
            setError(`Error: ${error.message}`);
          }
          setIsSubmitting(false);
        });
  }, [event, navigate, isSubmitting]);

  // Stable field change handler
  const handleChange = useCallback((e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setEvent((prevEvent) => {
      if (!prevEvent) return null;
      return {
        ...prevEvent,
        [name]: value
      };
    });
  }, []);

  // Stable skills change handler
  const handleSkillChange = useCallback((selectedOptions: readonly SkillOption[] | null) => {
    setEvent((prevEvent) => {
      if (!prevEvent) return null;
      return {
        ...prevEvent,
        skills: selectedOptions ? [...selectedOptions] : []
      };
    });
  }, []);

  // Handle navigation without causing re-renders
  const handleCancel = useCallback(() => {
    navigate('/AdminDashboard');
  }, [navigate]);

  if (loading && !event) {
    return <div>Loading event details...</div>;
  }

  if (error && !event) {
    return <div className="error-message">{error}</div>;
  }

  return (
      <div className="edit-event-container">
        <h2>Edit Event</h2>
        {error && <div className="error-message">{error}</div>}
        {event ? (
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="eventName">Event Name</label>
                <input
                    id="eventName"
                    type="text"
                    name="eventName"
                    value={event.eventName}
                    onChange={handleChange}
                    required
                    disabled={isSubmitting}
                />
              </div>
              <div className="form-group">
                <label htmlFor="eventDescription">Description</label>
                <textarea
                    id="eventDescription"
                    name="eventDescription"
                    value={event.eventDescription}
                    onChange={handleChange}
                    required
                    disabled={isSubmitting}
                />
              </div>
              <div className="form-group">
                <label htmlFor="location">Location</label>
                <input
                    id="location"
                    type="text"
                    name="location"
                    value={event.location}
                    onChange={handleChange}
                    required
                    disabled={isSubmitting}
                />
              </div>
              <div className="form-group">
                <label htmlFor="skills">Skills</label>
                <Select
                    inputId="skills"
                    isMulti
                    options={skillOptions}
                    value={event.skills}
                    onChange={handleSkillChange}
                    className="skills-select"
                    classNamePrefix="select"
                    isDisabled={isSubmitting}
                />
              </div>
              <div className="form-group">
                <label htmlFor="urgency">Urgency</label>
                <select
                    id="urgency"
                    name="urgency"
                    value={event.urgency}
                    onChange={handleChange}
                    required
                    disabled={isSubmitting}
                >
                  <option value="LOW">Low</option>
                  <option value="MEDIUM">Medium</option>
                  <option value="HIGH">High</option>
                </select>
              </div>
              <div className="form-group">
                <label htmlFor="eventDate">Event Date</label>
                <input
                    id="eventDate"
                    type="date"
                    name="eventDate"
                    value={event.eventDate}
                    onChange={handleChange}
                    required
                    disabled={isSubmitting}
                />
              </div>
              <div className="form-group">
                <label htmlFor="slotsFilled">Slots Filled</label>
                <input
                    id="slotsFilled"
                    type="number"
                    name="slotsFilled"
                    value={event.slotsFilled}
                    onChange={handleChange}
                    min="0"
                    required
                    disabled={isSubmitting}
                />
              </div>
              <div className="form-actions">
                <button
                    type="submit"
                    disabled={isSubmitting}
                >
                  {isSubmitting ? 'Updating...' : 'Update Event'}
                </button>
                <button
                    type="button"
                    onClick={handleCancel}
                    className="cancel-button"
                    disabled={isSubmitting}
                >
                  Cancel
                </button>
              </div>
            </form>
        ) : (
            <p>Could not load event details.</p>
        )}
      </div>
  );
};

export default EditEventForm;