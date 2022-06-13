CREATE TABLE role (
  id int NOT NULL AUTO_INCREMENT,
  method varchar(10) NOT NULL,
  role varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO role (id, method, role, name) VALUES (1, 'GET', 'LIST_USER', 'LISTAR USUARIOS');
INSERT INTO role (id, method, role, name) VALUES (2, 'GET', 'VIEW_USER', 'VISUALIZAR USUARIO');
INSERT INTO role (id, method, role, name) VALUES (3, 'POST', 'CREATE_USER', 'CRIAR USUARIO');
INSERT INTO role (id, method, role, name) VALUES (4, 'PUT', 'UPDATE_USER', 'ATUALIZAR USUARIO');
INSERT INTO role (id, method, role, name) VALUES (5, 'DELETE', 'DELETE_USER', 'EXCLUIR USUARIO');
