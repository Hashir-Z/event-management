import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AuthService from '../../services/AuthService';
import styles from "./SignUp.module.css";

function useQuery(): URLSearchParams {
  return new URLSearchParams(useLocation().search);
}

export function SignUp() {
  const navigate = useNavigate();
  const query = useQuery();
  const type = query.get("type");
  

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    street: "",
    city: "",
    state: "",
    zip: "",
  });

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    general: "",
  });

  const [isRunning, setIsRunning] = useState(false);
  const [, setIsSubmitted] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
    setErrors({ ...errors, [id]: "" }); 
  };

  const validate = (): boolean => {
    const newErrors: typeof errors = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      general: "",
    };
    let isValid = true;

    if (!formData.firstName) {
      newErrors.firstName = "Please enter your First Name";
      isValid = false;
    }
    if (!formData.lastName) {
      newErrors.lastName = "Please enter your Last Name";
      isValid = false;
    }
    if (!formData.email) {
      newErrors.email = "Please enter your Email";
      isValid = false;
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Invalid email format";
      isValid = false;
    }
    if (!formData.password) {
      newErrors.password = "Please enter your Password";
      isValid = false;
    } else if (
      !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_])[A-Za-z\d!@#$%^&*()_]{8,20}$/.test(
        formData.password
      )
    ) {
      newErrors.password =
        "Password must be between 8-20 characters, and must contain at least one uppercase and lowercase letter, one number, and one special character.";
      isValid = false;
    }
    if (!formData.confirmPassword) {
      newErrors.confirmPassword = "Please confirm your Password";
      isValid = false;
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = "Password and Confirm Password must match";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleRegistration = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsSubmitted(true);

    if (validate()) {
      setIsRunning(true);
      try {
        await AuthService.register({
          fullName: formData.firstName + " " + formData.lastName,
          email: formData.email,
          password: formData.password,
          isAdmin: type === "admin" ? true : false
        });

        setIsRunning(false);
        if (type === "admin") {
          navigate("/login?type=admin");
        } else {
          navigate("/login?type=volunteer");
        }
      } catch (error) {
        setIsRunning(false);
        setErrors((prevErrors) => ({
          ...prevErrors,
          general: "Failed to register. Please try again.",
        }));
      }
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.logoSection}>
        <img className={styles.logo} src="/logo-green.svg" alt="Logo" />
      </div>
      <div className={styles.signupSection}>
        <div className={styles.signupBox}>
          
          <div className={styles.title}>{type === "admin" ? "Admin Sign up" : "Volunteer Sign up"}</div>
          <form className={styles.form} onSubmit={handleRegistration}>
            {/* Name Fields */}
            <label className={styles.label} htmlFor="firstName">
              First Name
            </label>
            <input
              className={styles.input}
              type="text"
              id="firstName"
              placeholder="Your First Name"
              value={formData.firstName}
              onChange={handleChange}
            />
            {errors.firstName && (
              <div className={styles.error}>{errors.firstName}</div>
            )}

            <label className={styles.label} htmlFor="lastName">
              Last Name
            </label>
            <input
              className={styles.input}
              type="text"
              id="lastName"
              placeholder="Your Last Name"
              value={formData.lastName}
              onChange={handleChange}
            />
            {errors.lastName && (
              <div className={styles.error}>{errors.lastName}</div>
            )}

            {/* Email */}
            <label className={styles.label} htmlFor="email">
              Email Address
            </label>
            <input
              className={styles.input}
              type="email"
              id="email"
              placeholder="Email Address"
              value={formData.email}
              onChange={handleChange}
            />
            {errors.email && <div className={styles.error}>{errors.email}</div>}

            {/* Password */}
            <label className={styles.label} htmlFor="password">
              Password
            </label>
            <input
              className={styles.input}
              type="password"
              id="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
            />
            {errors.password && (
              <div className={styles.error}>{errors.password}</div>
            )}

            {/* Confirm Password */}
            <label className={styles.label} htmlFor="confirmPassword">
              Confirm Password
            </label>
            <input
              className={styles.input}
              type="password"
              id="confirmPassword"
              placeholder="Confirm Password"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
            {errors.confirmPassword && (
              <div className={styles.error}>{errors.confirmPassword}</div>
            )}

            {/* Submit Button */}
            <button className={styles.button} type="submit" disabled={isRunning}>
              {isRunning ? "Registering..." : "Sign Up"}
            </button>
            <button
              className={styles.button}
              onClick={() => navigate(`/login?type=${type === "admin" ? "admin" : "volunteer"}`)}
            >
              {type === "admin" ? "Already Have an Account? Sign in" : "Already Have an Account? Sign in"}
            </button>
            
            {/* General Error Message */}
            {errors.general && (
              <div className={styles.error}>{errors.general}</div>
            )}
          </form>
        </div>
        <button
          className={styles.volunteerBtn}
          onClick={() => navigate(`/signup?type=${type === "admin" ? "volunteer" : "admin"}`)}
        >
          {type === "admin" ? "Volunteer Sign up" : "Admin Sign up"}
        </button>
      </div>
    </div>
  );
}
