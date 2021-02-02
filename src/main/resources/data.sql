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