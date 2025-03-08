import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Login.module.css";
import AuthService from '../../services/AuthService';


function useQuery(): URLSearchParams {
  return new URLSearchParams(useLocation().search);
}

export function Login() {
  const query = useQuery();
  const type = query.get("type");
  const navigate = useNavigate();

  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [emailError, setEmailError] = useState<string>('');
  const [passwordError, setPasswordError] = useState<string>('');
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [loginError, setLoginError] = useState<string | null>(null);
  
  const validateEmail = (): boolean => {
    if (!email) {
      setEmailError("Please enter your email.");
      return false;
    }
    setEmailError("");
    return true;
  };

  const validatePassword = (): boolean => {
    if (!password) {
      setPasswordError("Please enter your password.");
      return false;
    } else if (password.length < 8) {
      setPasswordError("Password must be at least 8 characters long.");
      return false;
    }
    setPasswordError("");
    return true;
  };

  const handleSwitchLogin = (): void => {
    if (type === "admin") {
      navigate("/login?type=volunteer");
    } else {
      navigate("/login?type=admin");
    }
  };

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
  e.preventDefault();

  const isEmailValid = validateEmail();
  const isPasswordValid = validatePassword();

  if (isEmailValid && isPasswordValid) {
    setIsLoading(true);
    setLoginError(null);

    try {
      const response = await AuthService.login({
        email: email,
        password: password,
      });

      AuthService.setAccessToken(response.accessToken);
      AuthService.setRefreshToken(response.refreshToken);

      if (type === "admin") {
        navigate("/AdminDashboard");
      } else {
        navigate("/VolunteerDashboard");
      }
    } catch (error) {
      console.error("Login failed:", error);

      // Display error message for failed login
      setLoginError("Username or password is incorrect.");
    } finally {
      setIsLoading(false);
    }
  }
};

  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.loginSection}>
        <div className={styles.loginBox}>
          <div className={styles.title}>
            {type === "admin" ? "Admin Login" : "Volunteer Login"}
          </div>
          <form className={styles.form} onSubmit={handleLogin}>
            <label className={styles.label} htmlFor="email">Email</label>
            <input
              className={styles.input}
              type="email"
              id="email"
              placeholder="Email Address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onBlur={validateEmail}
              required
            />
            {emailError && <div className={styles.error}>{emailError}</div>}

            <label className={styles.label} htmlFor="password">Password</label>
            <input
              className={styles.input}
              type="password"
              id="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onBlur={validatePassword}
              required
            />
            {passwordError && <div className={styles.error}>{passwordError}</div>}

            {loginError && <div className={styles.error}>{loginError}</div>}
            
            <button
              className={styles.button}
              type="submit"
              disabled={isLoading}
            >
              {isLoading ? "Logging in..." : "Login"}
            </button>

            <div className={styles.links}>
              <a className={styles.link} href="#">Forgot password?</a>
                <a className={styles.link} href={`signup?type=${type === "admin" ? "admin" : "volunteer"}`} > Don't have an account? Sign up </a>
            </div>
          </form>
        </div>
        <button className={styles.volunteerBtn} onClick={handleSwitchLogin}>
          {type === "admin" ? "Volunteer Login" : "Admin Login"}
        </button>
      </div>
    </div>
  );
}
