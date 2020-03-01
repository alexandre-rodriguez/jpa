insert into produto (id, nome, preco, data_criacao, descricao) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome, preco, data_criacao, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');

insert into cliente (id, nome, cpf) values (1, 'Fernando Medeiros', '111.111.111-11');
insert into cliente_detalhe(cliente_id, sexo) values (1, 'MASCULINO');

insert into cliente (id, nome, cpf) values (2, 'Marcos Mariano', '222.222.222-22');
insert into cliente_detalhe(cliente_id, sexo) values (2, 'MASCULINO');

insert into cliente (id, nome, cpf) values (101, 'José da Silva', '333.333.333-33');
insert into cliente_detalhe(cliente_id, sexo) values (101, 'MASCULINO');

insert into pedido (id, cliente_id, data_criacao, total, status) values (1, 1, sysdate(), 2398.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 1, sysdate(), 499.0, 'AGUARDANDO');

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 3, 1400, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);

insert into pagamento(pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras) values(2, 'PROCESSANDO', 'cartao', '123', '');

insert into nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml />', sysdate());

insert into categoria (nome) values ('Eletrodomésticos');
insert into categoria (nome) values ('Livros');
insert into categoria (nome) values ('Esportes');
insert into categoria (nome) values ('Futebol');
insert into categoria (nome) values ('Natação');
insert into categoria (nome) values ('Notebooks');
insert into categoria (nome) values ('Smartphones');

insert into produto_categoria (produto_id, categoria_id) values (1, 2);
