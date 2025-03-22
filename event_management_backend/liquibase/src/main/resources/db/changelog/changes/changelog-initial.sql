## liquibase formatted sql

## Create table definitions for core tables

CREATE TABLE users (
                       id VARCHAR(36) NOT NULL PRIMARY KEY,
                       full_name VARCHAR(255) NOT NULL,
                       email VARCHAR(120) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       is_admin BOOLEAN NOT NULL DEFAULT FALSE,
                       INDEX idx_email (email)
);


CREATE TABLE token (
                       id VARCHAR(36) NOT NULL PRIMARY KEY,
                       expired BIT(1) NOT NULL DEFAULT b'0',
                       revoked BIT(1) NOT NULL DEFAULT b'0',
                       token VARCHAR(255) NOT NULL,
                       token_type VARCHAR(10) NOT NULL,
                       user_id VARCHAR(36) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       created_by VARCHAR(36) DEFAULT NULL,
                       modified_by VARCHAR(36) DEFAULT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE event_details(
                              event_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              event_name VARCHAR(255) NOT NULL,
                              event_description VARCHAR(500) NOT NULL,
                              location VARCHAR(255) NOT NULL,
                              required_skills VARCHAR(255) NOT NULL,
                              urgency VARCHAR(10) NOT NULL,
                              event_date  DATETIME DEFAULT NULL
);

CREATE TABLE volunteer_history(
                                  event_id INT UNSIGNED NOT NULL,
                                  user_id VARCHAR(36) NOT NULL,
                                  PRIMARY KEY (event_id, user_id),
                                  FOREIGN KEY (user_id) REFERENCES users(id),
                                  FOREIGN KEY (event_id) REFERENCES event_details(event_id)
);

CREATE TABLE user_profile_availability (
                                           user_profile_id VARCHAR(255) NOT NULL,
                                           availability VARCHAR(255) NOT NULL,
                                           PRIMARY KEY (user_profile_id, availability),
                                           FOREIGN KEY (user_profile_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE user_profile_skills (
                                     user_profile_id VARCHAR(255) NOT NULL,
                                     skill_id BIGINT NOT NULL,
                                     PRIMARY KEY (user_profile_id, skill_id),
                                     FOREIGN KEY (user_profile_id) REFERENCES user_profile(id) ON DELETE CASCADE,
                                     FOREIGN KEY (skill_id) REFERENCES skill_entity(id) ON DELETE CASCADE
);

CREATE TABLE event (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       event_name VARCHAR(255) NOT NULL,
                       event_description TEXT,
                       location VARCHAR(255),
                       skills TEXT, -- Stores selected skills as a comma-separated string
                       urgency VARCHAR(50),
                       event_date DATE,
                       slots_filled INT DEFAULT 0
);

CREATE TABLE event_skills (
                              event_id BIGINT,
                              skill_id BIGINT,
                              PRIMARY KEY (event_id, skill_id),
                              FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
                              FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

CREATE TABLE skill (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) UNIQUE NOT NULL
);
