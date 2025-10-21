-- ======= Base lookups =======
CREATE TABLE users_type (
  user_type_id INT AUTO_INCREMENT PRIMARY KEY,
  user_type_name VARCHAR(255)
);

INSERT INTO users_type (user_type_id, user_type_name) VALUES
  (1,'Recruiter'),
  (2,'Job Seeker');

-- ======= Users =======
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  is_active BOOLEAN,
  password VARCHAR(255),
  registration_date TIMESTAMP(6),
  user_type_id INT,
  UNIQUE (email),
  FOREIGN KEY (user_type_id) REFERENCES users_type (user_type_id)
);

-- ======= Companies & Locations =======
CREATE TABLE job_company (
  id INT AUTO_INCREMENT PRIMARY KEY,
  logo VARCHAR(255),
  name VARCHAR(255)
);

CREATE TABLE job_location (
  id INT AUTO_INCREMENT PRIMARY KEY,
  city VARCHAR(255),
  country VARCHAR(255),
  state VARCHAR(255)
);

-- ======= Profiles =======
CREATE TABLE job_seeker_profile (
  user_account_id INT PRIMARY KEY,
  city VARCHAR(255),
  country VARCHAR(255),
  employment_type VARCHAR(255),
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  profile_photo VARCHAR(255),
  resume VARCHAR(255),
  state VARCHAR(255),
  work_authorization VARCHAR(255),
  FOREIGN KEY (user_account_id) REFERENCES users (user_id)
);

CREATE TABLE recruiter_profile (
  user_account_id INT PRIMARY KEY,
  city VARCHAR(255),
  company VARCHAR(255),
  country VARCHAR(255),
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  profile_photo VARCHAR(64),
  state VARCHAR(255),
  FOREIGN KEY (user_account_id) REFERENCES users (user_id)
);

-- ======= Job posts =======
CREATE TABLE job_post_activity (
  job_post_id INT AUTO_INCREMENT PRIMARY KEY,
  description_of_job VARCHAR(10000),
  job_title VARCHAR(255),
  job_type VARCHAR(255),
  posted_date TIMESTAMP(6),
  remote VARCHAR(255),
  salary VARCHAR(255),
  job_company_id INT,
  job_location_id INT,
  posted_by_id INT,
  FOREIGN KEY (job_company_id) REFERENCES job_company (id),
  FOREIGN KEY (job_location_id) REFERENCES job_location (id),
  FOREIGN KEY (posted_by_id) REFERENCES users (user_id)
);

-- ======= Saved jobs =======
CREATE TABLE job_seeker_save (
  id INT AUTO_INCREMENT PRIMARY KEY,
  job INT,
  user_id INT,
  UNIQUE (user_id, job),
  FOREIGN KEY (job) REFERENCES job_post_activity (job_post_id),
  FOREIGN KEY (user_id) REFERENCES job_seeker_profile (user_account_id)
);

-- ======= Applications =======
CREATE TABLE job_seeker_apply (
  id INT AUTO_INCREMENT PRIMARY KEY,
  apply_date TIMESTAMP(6),
  cover_letter VARCHAR(255),
  job INT,
  user_id INT,
  UNIQUE (user_id, job),
  FOREIGN KEY (job) REFERENCES job_post_activity (job_post_id),
  FOREIGN KEY (user_id) REFERENCES job_seeker_profile (user_account_id)
);

-- ======= Skills =======
CREATE TABLE skills (
  id INT AUTO_INCREMENT PRIMARY KEY,
  experience_level VARCHAR(255),
  name VARCHAR(255),
  years_of_experience VARCHAR(255),
  job_seeker_profile INT,
  FOREIGN KEY (job_seeker_profile) REFERENCES job_seeker_profile (user_account_id)
);

-- ======= Helpful indexes (explicit) =======
CREATE INDEX idx_users_user_type_id ON users (user_type_id);
CREATE INDEX idx_job_post_activity_company  ON job_post_activity (job_company_id);
CREATE INDEX idx_job_post_activity_location ON job_post_activity (job_location_id);
CREATE INDEX idx_job_post_activity_postedby ON job_post_activity (posted_by_id);
CREATE INDEX idx_job_seeker_save_job        ON job_seeker_save (job);
CREATE INDEX idx_job_seeker_save_user       ON job_seeker_save (user_id);
CREATE INDEX idx_job_seeker_apply_job       ON job_seeker_apply (job);
CREATE INDEX idx_job_seeker_apply_user      ON job_seeker_apply (user_id);
CREATE INDEX idx_skills_profile             ON skills (job_seeker_profile);
