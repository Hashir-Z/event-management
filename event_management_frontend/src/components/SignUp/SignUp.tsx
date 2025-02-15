import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./SignUp.module.css";

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

export function SignUp() {
  const query = useQuery();
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate("/login?type=volunteer");
  };

  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.signupSection}>
        <div className={styles.signupBox}>
          <h2 className={styles.title}>Volunteer Sign Up</h2>
          <form className={styles.form}>
            <label className={styles.label} htmlFor="name">Name</label>
            <input className={styles.input} type="text" id="name" placeholder="Your Name" required />

            <label className={styles.label} htmlFor="street">Street</label>
            <input className={styles.input} type="text" id="street" placeholder="Street" required />

            <label className={styles.label} htmlFor="city">City</label>
            <input className={styles.input} type="text" id="city" placeholder="City" required />

            <label className={styles.label} htmlFor="state">State</label>
            <input className={styles.input} type="text" id="state" placeholder="State" required />

            <label className={styles.label} htmlFor="zip">Zip Code</label>
            <input className={styles.input} type="text" id="zip" placeholder="Zip Code" maxLength={9} pattern="\d{5}|\d{9}" required />

            <label className={styles.label} htmlFor="email">Email Address</label>
            <input className={styles.input} type="email" id="email" placeholder="Email Address" required />

            <label className={styles.label} htmlFor="password">Password</label>
            <input className={styles.input} type="password" id="password" placeholder="Password" required />

            <button className={styles.button} type="submit">Sign Up</button>
          </form>
        </div>
        <button className={styles.volunteerBtn} onClick={handleButtonClick}>Volunteer Sign In</button>
      </div>
    </div>
  );
}
