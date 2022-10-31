CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  id_person int NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(70) NOT NULL,
  active tinyint NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY user_UNIQUE (username),
  UNIQUE KEY id_person_UNIQUE (id_person),
  CONSTRAINT fk_person FOREIGN KEY (id_person) REFERENCES person (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user (id, id_person, username, password, active) VALUES (1, 1, '11573494304', '$2a$10$LvtCtBtxJyrviMbU.C/Re.nfj3xRBbRVdbzNcgj8pjURJAN9XlIWC', 1);

