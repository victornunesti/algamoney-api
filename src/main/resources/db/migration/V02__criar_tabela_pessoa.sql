CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(100),
    bairro VARCHAR(50),
    cep VARCHAR(10),
    cidade VARCHAR(50),
    estado VARCHAR(2)    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('João', true, '1', '20', 'casa', 'Serrano', '31380852', 'Belo Horizonte', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Jose', true, '2', '54', 'AP 302', 'Serrano', '31387852', 'Belo Horizonte', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Maria', true, 'Antonia falabela', '1', null, 'Pirati', '31380982', 'Belo Horizonte', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Carlos', true, '5', '312', 'fundos', 'Bom Sucesso', '31380880', 'Belo Horizonte', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Clara', true, 'Av Serrano', '45', null, 'Alfavile', '31345852', 'Almenara', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Elis', true, 'Julita', '49', null, 'Nobre', '31380122', 'Almenara', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Marcos', 'true', 'Castelo branco', '37', null, 'Ortega', '31383852', 'São Paulo', 'SP');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Roberto', true, '6', '51', null, 'Baixo Lurder', '31386352', 'Rio de Janeiro', 'RJ');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Pereira', true, '9', '47', null, 'Amaro', '31206352', 'Felixlandia', 'MG');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Ricardo', true, 'Morgado', '30', null, 'Paqueta', '31380972', 'Brasilia', 'GO');
