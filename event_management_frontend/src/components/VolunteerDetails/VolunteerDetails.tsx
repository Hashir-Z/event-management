import React, { useState, useEffect } from "react";
import Select from "react-select";
import "./VolunteerDetails.module.css";
import axios from "axios";
import axiosInstance from "../../services/AuthInterceptor";

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
    availability: [],
  });

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const skillOptions: SkillOption[] = [
    { value: "Skill 1", label: "Skill 1" },
    { value: "Skill 2", label: "Skill 2" },
    { value: "Skill 3", label: "Skill 3" },
  ];

  const stateOptions = [
    { value: "TX", label: "Texas" },
    { value: "CA", label: "California" },
    { value: "NY", label: "New York" },
    // Add other states as necessary
  ];

  const fetchUserProfile = async () => {
    try {
      const response = await axiosInstance.get("/api/user-profiles/all");
      if (response.data.length > 0) {
        const userProfile = response.data[0]; // Assuming you fetch the first profile

        setFormData({
          fullName: userProfile.fullName || "",
          address1: userProfile.address1 || "",
          address2: userProfile.address2 || "",
          city: userProfile.city || "",
          state: userProfile.state || "",
          zipCode: userProfile.zip || "",
          skills:
            userProfile.skills.map((skill: any) => ({
              value: String(skill.id),
              label: `Skill ${skill.id}`,
            })) || [],
          preferences: userProfile.preferences || "",
          availability:
            userProfile.availability.map((date: any) => date.availableDate) ||
            [],
        });
      }
    } catch (error) {
      console.error("Error fetching user profile:", error);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSkillChange = (
    selectedOptions: readonly { value: string; label: string }[] | null
  ) => {
    setFormData({
      ...formData,
      skills: selectedOptions
        ? ([...selectedOptions] as { value: string; label: string }[])
        : [],
    });
  };

  const handleAvailabilityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const date = e.target.value;
    setFormData({
      ...formData,
      availability: [...formData.availability, date],
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const accessToken = localStorage.getItem("access-token");

    if (!accessToken) {
      console.error("No access token found! Please log in first.");
      return;
    }

    const data = {
      fullName: formData.fullName,
      address1: formData.address1,
      address2: formData.address2,
      city: formData.city,
      state: formData.state,
      zip: formData.zipCode,
      preferences: formData.preferences,
      skills: formData.skills.map((skill) => ({ id: Number(skill.value) })), // Ensure valid numbers
      availability: formData.availability.map((date) => ({
        availableDate: new Date(date).toISOString().split("T")[0], // Properly format dates
      })),
    };

    try {
      console.log("Sending payload:", JSON.stringify(data, null, 2));

      // Make request using Axios instance
      const response = await axiosInstance.post("/api/user-profiles/add", data);

      console.log("Success:", response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios Error:", error.response?.data || error.message);
      } else {
        console.error("Unexpected Error:", error);
      }
    }
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
          <select
            name="state"
            value={formData.state}
            onChange={handleChange}
            required
          >
            <option value="">Select state</option>
            {stateOptions.map((state) => (
              <option key={state.value} value={state.value}>
                {state.label}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>
            Zip Code (9 characters, at least 5 characters required) *
          </label>
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
            value={
              formData.availability[formData.availability.length - 1] || ""
            }
            onChange={handleAvailabilityChange}
            required
          />
          <button
            type="button"
            onClick={() => {
              setFormData({
                ...formData,
                availability: [...formData.availability, ""], // Add an empty date slot
              });
            }}
          >
            Add Another Date
          </button>
        </div>

        <button type="submit" className="save-button">
          Save Profile
        </button>
      </form>
    </div>
  );
};

export default VolunteerDetails;
