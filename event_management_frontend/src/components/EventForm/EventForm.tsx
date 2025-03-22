import React, { useState } from "react";
import Select from "react-select";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./EventForm.module.css";

// Define TypeScript types
interface SkillOption {
  value: string;
  label: string;
}

interface FormData {
  eventName: string;
  eventDescription: string;
  skills: SkillOption[];
  urgency: string;
  location: string;
  eventDate: string;
  slotsFilled: number;
}

export const EventForm: React.FC = () => {
  const [formData, setFormData] = useState<FormData>({
    eventName: "",
    eventDescription: "",
    skills: [],
    urgency: "",
    location: "",
    eventDate: "",
    slotsFilled: 0,
  });

  const skillOptions: SkillOption[] = [
    { value: "COMMUNICATION", label: "Communication" },
    { value: "LEADERSHIP", label: "Leadership" },
    { value: "TEAMWORK", label: "Teamwork" },
    { value: "ORGANIZATION", label: "Organization" },
    { value: "TECHNICAL", label: "Technical" }
  ];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSkillChange = (selectedOptions: readonly { value: string; label: string }[] | null) => {
    setFormData({
      ...formData,
      skills: selectedOptions ? [...selectedOptions] as SkillOption[] : []
    });
  };

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const eventData = {
      eventName: formData.eventName,
      eventDescription: formData.eventDescription,
      skills: formData.skills.map(skill => skill.value), // Map skills to just the value
      urgency: formData.urgency,
      location: formData.location,
      eventDate: formData.eventDate,
      slotsFilled: 0,
    };

    console.log("Submitting event data:", eventData);
    try {
      const response = await axios.post("http://localhost:8080/api/events/add", eventData);
      console.log("Form Submitted Successfully", response.data);
    } catch (error) {
      console.error("Error submitting form", error);
    }

    navigate("/AdminDashboard");
  };

  return (
      <div className="form-container">
        <div className="title-box">
          <h2>Event Details</h2>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Event Name (100 characters, required) *</label>
            <input
                type="text"
                name="eventName"
                maxLength={100}
                value={formData.eventName}
                onChange={handleChange}
                required
            />
          </div>

          <div className="form-group">
            <label>Event Description (Text area, required) *</label>
            <textarea
                name="eventDescription"
                value={formData.eventDescription}
                onChange={handleChange}
                required
            />
          </div>

          <div className="form-group">
            <label>Location (Text area, required) *</label>
            <textarea
                name="location"
                value={formData.location}
                onChange={handleChange}
                required
            />
          </div>

          <div className="form-group">
            <label>Skills (Multi-select dropdown, required) *</label>
            <Select
                isMulti
                options={skillOptions}
                value={formData.skills}
                onChange={handleSkillChange}
            />
          </div>

          <div className="form-group">
            <label>Urgency (Drop down, selection required) *</label>
            <select name="urgency" value={formData.urgency} onChange={handleChange} required>
              <option value="">Select urgency</option>
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
          </div>

          <div className="form-group">
            <label>Event Date (Calendar, date picker)</label>
            <input type="date" name="eventDate" value={formData.eventDate} onChange={handleChange} />
          </div>

          <button type="submit" className="save-button">Save Event</button>
        </form>
      </div>
  );
};

export default EventForm;
