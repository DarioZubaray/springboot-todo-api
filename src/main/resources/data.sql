DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  id IDENTITY AUTO_INCREMENT PRIMARY KEY,
  message VARCHAR(250) NOT NULL,
  finished BOOLEAN NOT NULL,
  datetime TIMESTAMP DEFAULT NULL
);

INSERT INTO todo (message, finished, datetime) VALUES
  ('Hola mundo', false, '2021-02-01 13:00'),
  ('Aprender Java', true, '2021-02-01 13:01'),
  ('Aprender Spring', false, '2021-02-01 13:02');

DROP TABLE IF EXISTS todo_user;

CREATE TABLE todo_user (
  id IDENTITY AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO todo_user (username, password) VALUES
  ('admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6'),
  ('user', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');