import { Link } from "react-router-dom";
import styles from "./Homepage.module.css";

export const Homepage = () => {
  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.homeSection}>
        <h1 className={styles.title}>Welcome to Volunteer Management System</h1>
        <p className={styles.description}>
          Our non-profit organization is dedicated to efficiently managing and optimizing volunteer activities.
          Our software helps allocate volunteers to different events and tasks based on their preferences, skills,
          and availability. We consider volunteers location, event requirements, and task urgency to ensure the best match.
        </p>
        <div className={styles.links}>
          <Link to="/login?type=volunteer" className={styles.button}>Volunteer Login</Link>
          <Link to="/login?type=admin" className={styles.button}>Admin Login</Link>
        </div>
      </div>
    </div>
  );
}

export default Homepage;