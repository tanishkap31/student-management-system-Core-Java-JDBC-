CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL UNIQUE,
    course  VARCHAR(100) NOT NULL,
    age     INT NOT NULL CHECK (age > 0)
);

INSERT INTO students (name, email, course, age) VALUES
('Aarav Sharma', 'aarav@example.com', 'Core Java', 21),
('Priya Patil',  'priya@example.com', 'Advanced Java', 22);
