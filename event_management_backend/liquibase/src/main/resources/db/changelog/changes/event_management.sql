DROP TABLE IF EXISTS `users`;
CREATE TABLE users (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    INDEX idx_email (email)
);
INSERT INTO users (id, full_name, email, password, is_admin) VALUES
('a3c9', 'Alice Johnson', 'alice@example.com', 'hashed_pw_1', FALSE),
('b82f', 'Ben Carter', 'ben@example.com', 'hashed_pw_2', FALSE),
('d49f', 'Diana Lee', 'diana@example.com', 'hashed_pw_3', FALSE),
('f017', 'Frank Moore', 'frank@example.com', 'hashed_pw_4', FALSE);

DELETE FROM users;

DROP TABLE IF EXISTS `token`;
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
DROP TABLE IF EXISTS `event_details`;
CREATE TABLE `event_details`(
	event_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_description VARCHAR(500) NOT NULL,
    location VARCHAR(255) NOT NULL,
    required_skills VARCHAR(255) NOT NULL,
    urgency VARCHAR(10) NOT NULL,
    event_date  DATETIME DEFAULT NULL
);

INSERT INTO `event_details` (
    event_name, event_description, location, required_skills, urgency, event_date
) VALUES
('Beach Cleanup', 'Help remove litter and debris from the shoreline.', 'Santa Monica Beach', 'Teamwork, Stamina', 'High', '2025-05-10 09:00:00'),
('Park Tree Planting', 'Plant trees in the local park for Earth Day.', 'Central Park', 'Gardening, Lifting', 'Medium', '2025-05-15 10:00:00'),
('Food Drive', 'Organize and distribute food for the community.', 'Downtown Community Center', 'Organization, Communication', 'Low', '2025-05-20 13:00:00'),
('Charity Marathon', 'Assist with event logistics and hydration stations.', 'City Marathon Route', 'Coordination, Endurance', 'High', '2025-05-25 06:00:00'),
('Animal Shelter Volunteering', 'Support shelter operations and animal care.', 'Happy Paws Shelter', 'Animal Care, Patience', 'Medium', '2025-06-01 10:00:00'),
('Community Tutoring', 'Tutor students in basic subjects after school.', 'Lincoln High School', 'Teaching, Communication', 'Low', '2025-06-05 15:00:00');

DROP TABLE IF EXISTS `volunteer_history`;
CREATE TABLE volunteer_history(
	event_id INT UNSIGNED NOT NULL,
	user_id VARCHAR(36) NOT NULL,
    status ENUM('Confirmed', 'Attended', 'No-show') DEFAULT 'Confirmed',
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (user_id) REFERENCES volunteers(id),
    FOREIGN KEY (event_id) REFERENCES `event_details`(event_id)
);

INSERT INTO volunteer_history (event_id, user_id, status) VALUES
(1, 'a3c9', 'Confirmed'),
(1, 'b82f', 'Attended'),
(2, 'a3c9', 'No-show'),
(3, 'd49f', 'Attended'),
(4, 'b82f', 'Confirmed'),
(4, 'f017', 'Attended'),
(5, 'a3c9', 'Attended'),
(6, 'd49f', 'No-show'),
(6, 'f017', 'Confirmed');

DROP TABLE IF EXISTS `volunteers`;
CREATE TABLE volunteers (
   id VARCHAR(36) NOT NULL PRIMARY KEY,
   full_name VARCHAR(255) NOT NULL,
   location VARCHAR(100),
   FOREIGN KEY (id) REFERENCES users(id)
);
INSERT INTO volunteers (id, full_name, location) VALUES
('a3c9', 'Alice Johnson', 'Los Angeles, CA'),
('b82f', 'Ben Carter', 'New York, NY'),
('d49f', 'Diana Lee', 'Chicago, IL'),
('f017', 'Frank Moore', 'San Francisco, CA');


CREATE TABLE skills(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE preferences(
  id INT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id VARCHAR(36) NOT NULL,
  preference_type VARCHAR(50),  -- e.g., 'Outdoor Events', 'Logistics', 'Teaching'
  FOREIGN KEY (volunteer_id) REFERENCES volunteers(id)
);


CREATE TABLE availability (
  id INT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id VARCHAR(36) NOT NULL,
  weekday ENUM('Mon','Tue','Wed','Thu','Fri','Sat','Sun'),
  start_time TIME,
  end_time TIME,
  recurring BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (volunteer_id) REFERENCES volunteers(id)
);


DROP TABLE IF EXISTS `tasks`;
CREATE TABLE tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  event_id INT UNSIGNED NOT NULL,
  name VARCHAR(100),
  description TEXT,
  location VARCHAR(100),
  start_datetime DATETIME,
  end_datetime DATETIME,
  urgency_level ENUM('Low', 'Medium', 'High'),
  required_volunteers INT,
  FOREIGN KEY (event_id) REFERENCES `event_details`(event_id)
);



DROP TABLE IF EXISTS `task_skills`;
CREATE TABLE task_skills (
  task_id INT,
  skill_id INT,
  FOREIGN KEY (task_id) REFERENCES tasks(id),
  FOREIGN KEY (skill_id) REFERENCES skills(id),
  PRIMARY KEY (task_id, skill_id)
);

DROP TABLE IF EXISTS `assignments`;
CREATE TABLE assignments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  task_id INT,
  volunteer_id VARCHAR(36) NOT NULL,
  assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  status ENUM('Pending', 'Confirmed', 'Declined') DEFAULT 'Pending',
  FOREIGN KEY (task_id) REFERENCES tasks(id),
  FOREIGN KEY (volunteer_id) REFERENCES volunteers(id)
);

DROP VIEW volunteer_history_with_details

CREATE VIEW volunteer_history_with_details AS
SELECT 
    v.id AS volunteer_id,
    v.full_name AS volunteer_name,
    v.location AS volunteer_location,
    e.event_id,
    e.event_name,
    e.event_description,
    e.location AS event_location,
    e.required_skills,
    e.urgency,
    e.event_date,
    vh.status AS participation_status
FROM 
    volunteers v
JOIN 
    volunteer_history vh ON v.id = vh.user_id
JOIN 
    event_details e ON vh.event_id = e.event_id;

