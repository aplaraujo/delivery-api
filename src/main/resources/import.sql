INSERT INTO usuario(email, senha, nome, role, ativo, data_criacao, restaurante_id) VALUES('admin@gmail.com', '$2a$16$0YaZ8FJiup1qz2qq3kRkau..hCpivGw.bpsXNWa0wYEuXDAeSkjFS', 'admin', 'ADMIN', true, '2026-04-23T00:37:29.818641174', 0);

INSERT INTO cliente(nome, email, ativo, data_criacao) VALUES('Caroline Nair da Costa', 'carolinenairdacosta@outllok.com', true, '2026-04-23T00:37:29.818641174');
INSERT INTO cliente(nome, email, ativo, data_criacao) VALUES('Hugo Vitor Martins', 'hugovitormartins@iclud.com', true, '2026-04-23T00:37:29.818641174');
INSERT INTO cliente(nome, email, ativo, data_criacao) VALUES('Giovanni Henrique Cavalcanti', 'giovannihenriquecavalcanti@megamega.com.br', true, '2026-04-23T00:37:29.818641174');
INSERT INTO cliente(nome, email, ativo, data_criacao) VALUES('Rafael Miguel Bernardes', 'rafael_miguel_bernardes@cosma.com', true, '2026-04-23T00:37:29.818641174');
INSERT INTO cliente(nome, email, ativo, data_criacao) VALUES('Isis Sophia Dias', 'isis_dias@iclud.com', true, '2026-04-23T00:37:29.818641174');

INSERT INTO restaurante(nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES('Cantina do Nono', 'Italiana', '(11) 91234-5678', 5.99, 40, true);
INSERT INTO restaurante(nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES('Sabor Oriental', 'Japonesa', '(11) 98765-4321', 7.50, 50, true);
INSERT INTO restaurante(nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES('Churrascaria Gaúcha', 'Brasileira', '(51) 93344-2211', 0.00, 35, true);
INSERT INTO restaurante(nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES('Burger Street', 'Lanches', '(21) 97788-9900', 4.99, 25, true);
INSERT INTO restaurante(nome, categoria, telefone, taxa_entrega, tempo_entrega_minutos, ativo) VALUES('Taco Loco', 'Mexicana', '(31) 96655-1122', 6.00, 45, true);

INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Spaghetti Carbonara', 'Massas', 'Massa com molho cremoso de ovos, pancetta e queijo pecorino.', 42.90, 1, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Lasanha Bolonhesa', 'Massas', 'Camadas de massa fresca com ragu de carne e bechamel gratinado.', 42.90, 1, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Fettuccine Alfredo', 'Massas', 'Fettuccine ao molho de manteiga e parmesao cremoso.', 44.90, 1, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Combo Sushi 20 Pecas', 'Combinados', 'Selecao de niguiris, hossomakis e uramakis do chef.', 89.90, 2, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Temaki Salmao', 'Temaki', 'Cone de alga nori com salmao, cream cheese e cebolinha.', 32.90, 2, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Temaki Atum', 'Temaki', 'Cone de alga nori com atum fresco e pepino.', 34.90, 2, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Porco na Brasa', 'Carnes', 'Costelinha suína marinada e grelhada na lenha.', 59.90, 3, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Salada de Rúcula', 'Saladas', 'Rúcula com tomate cereja, parmesão e azeite balsâmico.', 22.90, 3, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Arroz Carreteiro', 'Acompanhamentos', 'Arroz com charque e temperos gaúchos tradicionais.', 28.90, 3, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Smash Burger Clássico', 'Hambúrgueres', 'Blend de fraldinha 180g, cheddar, picles e molho da casa.', 34.90, 4, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Hambúrgueres', 'Hambúrgueres', 'Frango empanado crocante, maionese de alho, alface e tomate.', 29.90, 4, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Double Smash', 'Hambúrgueres', 'Dois blends de fraldinha 150g, duplo cheddar e bacon.', 44.90, 4, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Burrito de Carne', 'Burritos', 'Tortilha com carne temperada, arroz mexicano, feijão e guacamole.', 38.50, 5, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Nachos com Queijo', 'Entradas', '"Tortilhas crocantes com molho de queijo, jalapeño e pico de gallo.', 24.90, 5, true);
INSERT INTO produto(nome, categoria, descricao, preco, restaurante_id, disponivel) VALUES('Taco de Carnitas', 'Tacos', 'Tortilha de milho com porco desfiado, coentro e cebola.', 19.90, 5, true);