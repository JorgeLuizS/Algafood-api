ALTER TABLE algafood.restaurante ADD ativo TINYINT DEFAULT true NOT NULL;
update restaurante set ativo = true;