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

-- Productos
-- Filtros
INSERT INTO stock (quantity) VALUES (150);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Filtro de aceite', 120.00, 1, 1, 1);

INSERT INTO stock (quantity) VALUES (120);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Filtro de aire', 80.00, 1, 2, 2);

-- Baterías
INSERT INTO stock (quantity) VALUES (90);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Batería 12V', 850.00, 2, 3, 2);

INSERT INTO stock (quantity) VALUES (70);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Batería 24V', 1100.00, 2, 4, 1);

-- Llantas
INSERT INTO stock (quantity) VALUES (60);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Llanta 15"', 350.00, 3, 5, 3);

INSERT INTO stock (quantity) VALUES (80);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Llanta 16"', 400.00, 3, 6, 4);

-- Frenos
INSERT INTO stock (quantity) VALUES (200);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Pastillas de freno', 95.00, 4, 7, 1);

INSERT INTO stock (quantity) VALUES (150);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Disco de freno', 180.00, 4, 8, 5);

-- Aceites
INSERT INTO stock (quantity) VALUES (300);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Aceite sintético', 60.00, 5, 9, 1);

INSERT INTO stock (quantity) VALUES (250);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Aceite semi-sintético', 55.00, 5, 10, 2);

-- Luces
INSERT INTO stock (quantity) VALUES (500);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Luz halógena', 35.00, 6, 11, 2);

INSERT INTO stock (quantity) VALUES (450);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Luz LED', 45.00, 6, 12, 1);

-- Escape
INSERT INTO stock (quantity) VALUES (60);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Sistema de escape deportivo', 600.00, 7, 13, 4);

INSERT INTO stock (quantity) VALUES (80);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Silenciador', 450.00, 7, 14, 1);

-- Suspensión
INSERT INTO stock (quantity) VALUES (100);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Amortiguador delantero', 300.00, 8, 15, 5);

INSERT INTO stock (quantity) VALUES (90);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Amortiguador trasero', 280.00, 8, 16, 2);

-- Encendido
INSERT INTO stock (quantity) VALUES (150);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Bujía de encendido', 20.00, 9, 17, 2);

INSERT INTO stock (quantity) VALUES (200);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Bobina de encendido', 120.00, 9, 18, 1);

-- Correas
INSERT INTO stock (quantity) VALUES (300);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Correa de distribución', 75.00, 10, 19, 1);

INSERT INTO stock (quantity) VALUES (250);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Correa de alternador', 65.00, 10, 20, 2);

-- Ejemplo adicional (producto #21)
INSERT INTO stock (quantity) VALUES (140);
INSERT INTO products (name, price, category_id, stock_id, brand_id) VALUES
('Filtro de combustible', 90.00, 1, 21, 5);

