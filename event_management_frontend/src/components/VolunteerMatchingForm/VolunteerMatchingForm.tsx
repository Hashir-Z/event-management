import React from 'react';

import styles from './VolunteerMatchingForm.module.css';


export function VolunteerMatchingForm() {
  return(
    <div className={styles.VolunteerMatchingForm}>
        <div className={styles.title}> Volunteer Matching Form </div>
        <div className={styles.form}>
        <h2>Volunteer Matching Form</h2>
        <form id="volunteerForm">
          <label htmlFor="firstName">First Name: <span className={styles.required}>*</span></label>
          <input type="text" id="firstName" name="firstName" required/> 

          <label htmlFor="lastName">Last Name: <span className={styles.required}>*</span></label>
          <input type="text" id="lastName" name="lastName" required/> 

          <label htmlFor="email">Email: <span className={styles.required}>*</span></label>
          <input type="email" id="email" name="email" required></input>

          <label htmlFor="phoneNumber">Phone Number: </label>
          <input type="text" id="phoneNumber" name="phoneNumber"></input>

          <label htmlFor="location">Location: <span className={styles.required}>*</span></label>
          <input type="text" id="location" name="location" required></input>

          <label htmlFor="availability">Availability: <span className={styles.required}>*</span></label>
            <select id="availability" name="availability">
                <option value="weekdays">Weekdays</option>
                <option value="weekends">Weekends</option>
                <option value="anytime">Anytime</option>
            </select>
          
            <label htmlFor="skills">Skills: <span className={styles.required}>*</span></label>
            <input type="text" id="skills" name="skills"/>

            <label htmlFor="interests">Interests:</label>
            <textarea id="interests" name="interests"></textarea>

            <button type="submit">Submit</button>

        </form>
        </div>
    </div>

  );
}
