insert into usuario values (1,'admin@gmail.com','ROLE_ADMIN','$2a$12$Go7lNUjO/FbknnJUD.PBBejfP0YfmRsGjcvJxGQO3tZylnG2kgJkW');
insert into usuario values (2,'usuario@gmail.com','ROLE_USER','$2a$12$b5r9lDX.TrqBRBpclPsJreZ.B1WjiglQqyyktAVqYf5OhnU7mP5.C');

INSERT INTO cliente (nome, cpf, logradouro, bairro, cidade, uf, cep, complemento)
VALUES
('Carlos Silva', '12345678901', 'Rua das Flores, 120', 'Centro', 'São Paulo', 'SP', '01001-000', 'Apto 12'),
('Ana Pereira', '98765432100', 'Av. Atlântica, 450', 'Copacabana', 'Rio de Janeiro', 'RJ', '22010-000', 'Bloco B'),
('João Souza', '55566677788', 'Rua XV de Novembro, 75', 'Centro', 'Curitiba', 'PR', '80020-310', NULL);

-- Inserir telefones (usando a sequência gerada pelo Hibernate)
INSERT INTO telefone (id, numero, tipo) VALUES
(1, '11987654321', 'CELULAR'),
(2, '1123456789', 'RESIDENCIAL'),
(3, '21999998888', 'CELULAR'),
(4, '4133221100', 'COMERCIAL');


-- Vincular clientes e telefones
-- Supondo que IDs dos clientes sejam 1, 2 e 3 após inserção
INSERT INTO cliente_telefone (cliente_id, telefone_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4);

-- Inserir e-mails dos clientes
INSERT INTO emails (cliente_id, email) VALUES
(1, 'carlos.silva@example.com'),
(1, 'c.silva@gmail.com'),
(2, 'ana.pereira@example.com'),
(2, 'ana_p@hotmail.com'),
(3, 'joao.souza@example.com');