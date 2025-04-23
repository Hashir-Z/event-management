import React, { useState } from "react";
import Select from "react-select";
import { useNavigate } from "react-router-dom";
import "./VolunteerDetails.module.css";

// Define TypeScript types
interface SkillOption {
  value: string;
  label: string;
}

interface FormData {
  fullName: string;
  address1: string;
  address2: string;
  city: string;
  state: string;
  zipCode: string;
  skills: SkillOption[];
  preferences: string;
  availability: string[];
}



export const VolunteerDetails: React.FC = () => {
  
  const [formData, setFormData] = useState<FormData>({
    fullName: "",
    address1: "",
    address2: "",
    city: "",
    state: "",
    zipCode: "",
    skills: [],
    preferences: "",
    availability: []
  });

  const skillOptions: SkillOption[] = [
    { value: "Skill 1", label: "Skill 1" },
    { value: "Skill 2", label: "Skill 2" },
    { value: "Skill 3", label: "Skill 3" }
  ];

  const stateOptions = [
    { value: "TX", label: "Texas" },
    { value: "CA", label: "California" },
    { value: "NY", label: "New York" }
    // Add other states as necessary
  ];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSkillChange = (selectedOptions: readonly { value: string; label: string }[] | null) => {
    setFormData({
      ...formData,
      skills: selectedOptions ? [...selectedOptions] as { value: string; label: string }[] : []
    });
  };

  const handleAvailabilityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const date = e.target.value;
    setFormData({
      ...formData,
      availability: [...formData.availability, date]
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const data = {
      fullName: formData.fullName,
      address1: formData.address1,
      address2: formData.address2,
      city: formData.city,
      state: formData.state,
      zipCode: formData.zipCode,
      skills: formData.skills.map((skill) => skill.value), // Send only the value of the skills
      preferences: formData.preferences,
      availability: formData.availability,
    };
  
    // Post data to the backend
    fetch('http://localhost:8082/api/user-profiles/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data), // Send form data as JSON
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Success:', data); // Handle success, possibly show a confirmation message
      })
      .catch((error) => {
        console.error('Error:', error); // Handle error
      });
  };

  return (
    <div className="form-container">
      <div className="title-box">
        <h2>Volunteer Profile Details</h2>
      </div>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Full Name (50 characters, required) *</label>
          <input
            type="text"
            name="fullName"
            maxLength={50}
            value={formData.fullName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Address 1 (100 characters, required) *</label>
          <input
            type="text"
            name="address1"
            maxLength={100}
            value={formData.address1}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Address 2 (100 characters, optional)</label>
          <input
            type="text"
            name="address2"
            maxLength={100}
            value={formData.address2}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>City (100 characters, required) *</label>
          <input
            type="text"
            name="city"
            maxLength={100}
            value={formData.city}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>State (Drop Down, selection required) *</label>
          <select name="state" value={formData.state} onChange={handleChange} required>
            <option value="">Select state</option>
            {stateOptions.map((state) => (
              <option key={state.value} value={state.value}>
                {state.label}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Zip Code (9 characters, at least 5 characters required) *</label>
          <input
            type="text"
            name="zipCode"
            maxLength={9}
            value={formData.zipCode}
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
          <label>Preferences (Text area, optional)</label>
          <textarea
            name="preferences"
            value={formData.preferences}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Availability (Multiple Dates, required)</label>
          <input
    type="date"
    name="availability"
    value={formData.availability[formData.availability.length - 1] || ""}
    onChange={handleAvailabilityChange}
    required
  />
  <button
    type="button"
    onClick={() => {
      setFormData({
        ...formData,
        availability: [...formData.availability, ''] // Add an empty date slot
      });
    }}
  >
    Add Another Date
  </button>
        </div>

        <button type="submit" className="save-button">Save Profile</button>
      </form>
    </div>
  );
};

export default VolunteerDetails;
