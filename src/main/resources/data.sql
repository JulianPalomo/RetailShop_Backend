
-- Marcas
INSERT INTO brands (name) VALUES ('Bosch');
INSERT INTO brands (name) VALUES ('Ford');
INSERT INTO brands (name) VALUES ('Michelin');
INSERT INTO brands (name) VALUES ('Genirico');
INSERT INTO brands (name) VALUES ('ACDelco');

-- Categorías
INSERT INTO categories (name) VALUES ('Filtros');
INSERT INTO categories (name) VALUES ('Baterías');
INSERT INTO categories (name) VALUES ('Llantas');
INSERT INTO categories (name) VALUES ('Frenos');
INSERT INTO categories (name) VALUES ('Aceites');
INSERT INTO categories (name) VALUES ('Luces');
INSERT INTO categories (name) VALUES ('Escape');
INSERT INTO categories (name) VALUES ('Suspensión');
INSERT INTO categories (name) VALUES ('Encendido');
INSERT INTO categories (name) VALUES ('Correas');

-- Filtros
INSERT INTO stock (quantity,minimun_stock) VALUES (150,10);
INSERT INTO products (name,sku, price, category_id, stock_id, brand_id,is_active) VALUES
('Filtro de aceite','F1', 120.00, 1, 1, 1,true);

INSERT INTO stock (quantity,minimun_stock) VALUES (150,10);
INSERT INTO products (name,sku, price, category_id, stock_id, brand_id,is_active) VALUES
('Filtro de aire','F1', 80.00, 1, 2, 2,true);

-- Baterías
INSERT INTO stock (quantity,minimun_stock) VALUES (150,10);
INSERT INTO products (name,sku, price, category_id, stock_id, brand_id,is_active) VALUES
('Batería 12V','B1', 850.00, 2, 3, 2,true);

INSERT INTO stock (quantity,minimun_stock) VALUES (150,10);
INSERT INTO products (name,sku, price, category_id, stock_id, brand_id,is_active) VALUES
('Batería 24V','B1', 1100.00, 2, 4, 1,true);