CREATE TABLE restaurante_usuario_responsavel (
  restaurante_id bigint(20) NOT NULL,
  usuario_id bigint(20) NOT NULL,
  PRIMARY KEY (restaurante_id, usuario_id),
  KEY restaurante_usuario_responsavel_usuario_FK (usuario_id),
  CONSTRAINT restaurante_usuario_responsavel_restaurante_FK FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
  CONSTRAINT restaurante_usuario_responsavel_usuario_FK FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;