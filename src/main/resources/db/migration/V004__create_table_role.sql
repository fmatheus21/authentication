CREATE TABLE role (
  id int NOT NULL AUTO_INCREMENT,
  id_application int NOT NULL,
  role varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY role_UNIQUE (role),
  KEY fk_app_role_idx (id_application),
  CONSTRAINT fk_app_role FOREIGN KEY (id_application) REFERENCES application (id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO role (id, id_application, role, name) VALUES (1, 1, 'ECOMMERCE_LIST_USER', 'LISTAR USUARIOS');
INSERT INTO role (id, id_application, role, name) VALUES (2, 1, 'ECOMMERCE_VIEW_USER', 'VISUALIZAR USUARIO');
INSERT INTO role (id, id_application, role, name) VALUES (3, 1, 'ECOMMERCE_CREATE_USER', 'CRIAR USUARIO');
INSERT INTO role (id, id_application, role, name) VALUES (4, 1, 'ECOMMERCE_UPDATE_USER', 'ATUALIZAR USUARIO');
INSERT INTO role (id, id_application, role, name) VALUES (5, 1, 'ECOMMERCE_DELETE_USER', 'EXCLUIR USUARIO');
