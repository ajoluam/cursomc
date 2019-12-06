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