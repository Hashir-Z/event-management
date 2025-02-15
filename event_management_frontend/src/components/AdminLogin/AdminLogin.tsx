import React from "react";
import styles from "./AdminLogin.module.css";

export function AdminLogin() {
  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.loginSection}>
        <div className={styles.loginBox}>
          <h2 className={styles.title}>Admin Login</h2>
          <form className={styles.form}>
            <label className={styles.label} htmlFor="email">Email</label>
            <input className={styles.input} type="email" id="email" placeholder="Value" required />
            
            <label className={styles.label} htmlFor="password">Password</label>
            <input className={styles.input} type="password" id="password" placeholder="Value" required />
            
            <button className={styles.button} type="submit">Sign In</button>

            <div className={styles.links}>
              <a className={styles.link} href="#">Forgot password?</a>
              <a className={styles.link} href="#">Don't have an account? Sign up</a>
            </div>
          </form>
        </div>
        <button className={styles.volunteerBtn}>Volunteer Sign In</button>
      </div>
    </div>
  );
}
