import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Login.module.css";

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

export function Login() {
  const query = useQuery();
  const type = query.get("type");
  const navigate = useNavigate();

  const handleButtonClick = () => {
    if (type === "admin") {
      navigate("/login?type=volunteer");
    } else {
      navigate("/login?type=admin");
    }
  };

  const login = () => {
    if (type === "admin") {
      navigate("/AdminDashboard");
    } else {
      navigate("/VolunteerDashboard");
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.loginSection}>
        <div className={styles.loginBox}>
          <h2 className={styles.title}>{type === "admin" ? "Admin Login" : "Volunteer Login"}</h2>
          <form className={styles.form}>
            <label className={styles.label} htmlFor="email">Email</label>
            <input className={styles.input} type="email" id="email" placeholder="Email Address" required />

            <label className={styles.label} htmlFor="password">Password</label>
            <input className={styles.input} type="password" id="password" placeholder="Password" required />

            <button className={styles.button} type="submit" onClick={login} >Login</button>

            <div className={styles.links}>
              <a className={styles.link} href="#">Forgot password?</a>
              {type === "volunteer" && (
                <a className={styles.link} href="#">Don't have an account? Sign up</a>
              )}
            </div>
          </form>
        </div>
        <button className={styles.volunteerBtn} onClick={handleButtonClick}>{type === "admin" ? "Volunteer Login" : "Admin Login"}</button>
      </div>
    </div>
  );
}
