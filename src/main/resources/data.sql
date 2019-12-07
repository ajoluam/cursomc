insert into categoria(nome) values ('Informativa')
insert into categoria(nome) values ('Escritorio')

insert into produto(nome, preco) values ('Computador', 2000.00)
insert into produto(nome, preco) values ('Impressora', 800.00)
insert into produto(nome, preco) values ('Mouse', 80.00)

insert into produto_categoria(produto_id, categoria_id) values (1,1)
insert into produto_categoria(produto_id, categoria_id) values (2,1)
insert into produto_categoria(produto_id, categoria_id) values (2,2)
insert into produto_categoria(produto_id, categoria_id) values (3,1)

insert into estado(nome) values ('Minas Geais'), ('São Paulo')
insert into cidade(nome, estado_id) values ('Uberlância', 1) , ('São Paulo', 2), ('Campinas', 2)

insert into cliente (nome, email, cpf_ou_cnpj, tipo) values ('Maria Silva', 'maria@gmail.com', '02606030400', 1)

insert into telefone(telefones, cliente_id) values ('27363323', 1), ('93838393', 1)


insert into endereco(logradouro, numero, complemento, bairro, cep, cidade_id, cliente_id) values ('Rua Flores', '300', 'Apto 203', 'Jardim', '38220834', 1, 1),('Avenida Matos', '105', 'Sala 800', 'Centro', '38777012', 2, 1)
					