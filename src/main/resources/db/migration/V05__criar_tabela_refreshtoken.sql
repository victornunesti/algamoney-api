CREATE TABLE refreshtoken(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(200) NOT NULL UNIQUE,
    expiry_date DATE NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;