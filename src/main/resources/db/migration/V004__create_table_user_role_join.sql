CREATE TABLE user_role_join (
  id_user int NOT NULL,
  id_role int NOT NULL,
  PRIMARY KEY (id_user,id_role),
  KEY fk_authentication_idx (id_user),
  KEY fk_permission_idx (id_role),
  CONSTRAINT fk_permission FOREIGN KEY (id_role) REFERENCES role (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user_role_join (id_user, id_role) VALUES (1, 1);
INSERT INTO user_role_join (id_user, id_role) VALUES (1, 2);
INSERT INTO user_role_join (id_user, id_role) VALUES (1, 3);
INSERT INTO user_role_join (id_user, id_role) VALUES (1, 4);
INSERT INTO user_role_join (id_user, id_role) VALUES (1, 5);
