
-- Inserción de categorías
INSERT INTO categories (name) VALUES ('Motor');
INSERT INTO categories (name) VALUES ('Transmisión');
INSERT INTO categories (name) VALUES ('Suspensión');
INSERT INTO categories (name) VALUES ('Frenos');
INSERT INTO categories (name) VALUES ('Electrónica');
INSERT INTO categories (name) VALUES ('Iluminación');
INSERT INTO categories (name) VALUES ('Interior');
INSERT INTO categories (name) VALUES ('Exterior');
INSERT INTO categories (name) VALUES ('Ruedas');
INSERT INTO categories (name) VALUES ('Escapes');

-- Inserción de productos
INSERT INTO products (name, price, stock, category_id) VALUES ('Filtro de Aire', 150.00, 50, 1);
INSERT INTO products (name, price, stock, category_id) VALUES ('Aceite de Motor', 350.00, 200, 1);
INSERT INTO products (name, price, stock, category_id) VALUES ('Caja de Cambios', 1500.00, 10, 2);
INSERT INTO products (name, price, stock, category_id) VALUES ('Amortiguadores', 600.00,  75, 3);
INSERT INTO products (name, price, stock, category_id) VALUES ('Pastillas de Freno', 120.00, 150, 4);
INSERT INTO products (name, price, stock, category_id) VALUES ('Batería', 800.00,  30, 5);
INSERT INTO products (name, price,stock, category_id) VALUES ('Faros LED', 250.00,  80, 6);
INSERT INTO products (name, price,  stock, category_id) VALUES ('Tapizado de Asientos', 500.00, 20, 7);
INSERT INTO products (name, price,  stock, category_id) VALUES ('Espejo Retrovisor', 75.00,  60, 8);
INSERT INTO products (name, price, stock, category_id) VALUES ('Llanta Deportiva', 450.00,  40, 9);
INSERT INTO products (name, price,  stock, category_id) VALUES ('Silenciador', 300.00,  15, 10);