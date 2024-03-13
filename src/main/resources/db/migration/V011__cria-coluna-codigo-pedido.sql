ALTER TABLE algafood.pedido ADD codigo varchar(36) DEFAULT UUID() NOT NULL after id;
ALTER TABLE algafood.pedido ADD CONSTRAINT uk_pedido_unique UNIQUE KEY (codigo);
