CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(40) NOT NULL,
	logradouro VARCHAR(30),
	numero VARCHAR(5),
	complemento VARCHAR(20),
	bairro VARCHAR(20),
	cep VARCHAR(9),
	cidade VARCHAR(20),
	estado VARCHAR(15),
	ativo BOOLEAN NOT NULL
)ENGINE=InnoDB;

INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Emerson Barros','rua Benedito Cardoso de Barros','269','casa','Agua Fria','07752-170','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Kauã Barros','rua sumaré','8','casa','Jd. Muriano','07752-000','Cajamar','São Paulo',1);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Alice Barros','Av. Deovair Cruz de Oliveira','239','Comércio','Jordanésia','07776-435','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Joselma Lira','rua xxx','10','casa','Polvilho','07752-000','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,ativo)values('Millena melo',false);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('João Barros','Joaquim Pereira Barbosa','244','Comércio','Jordanésia','07752-000','Cajamar','São Paulo',false);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Maria Celeste','rua das paineiras','334','casa','Gato Preto','07752-000','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Kauã Barros','rua sumaré','8','casa','Jd. Muriano','07752-000','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Douglas Barros','rua São José','8','casa','Gato Preto','07752-000','Cajamar','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo)values('Kelly Barros','rua sumaré','8','casa','Jd. Muriano','07752-000','Cajamar','São Paulo',false);