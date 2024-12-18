-- Insertar categorías
INSERT INTO categories (name) VALUES
('Frenos'),
('Suspensión'),
('Motor'),
('Transmisión'),
('Escape'),
('Eléctrico'),
('Accesorios'),
('Carrocería'),
('Refrigeración'),
('Dirección');

-- Insertar productos
INSERT INTO products (sku, name, price, stock, minimum_stock, is_active, category_id) VALUES
('BRAKE01', 'Pastillas de Freno Delanteras', 45.99, 100, 10, TRUE, 1),
('BRAKE02', 'Discos de Freno Traseros', 120.00, 50, 5, TRUE, 1),
('SUSP01', 'Amortiguadores Delanteros', 89.50, 30, 5, TRUE, 2),
('SUSP02', 'Resortes de Suspensión', 55.75, 20, 3, TRUE, 2),
('ENGINE01', 'Filtro de Aceite', 12.99, 200, 20, TRUE, 3),
('ENGINE02', 'Bomba de Agua', 75.00, 40, 5, TRUE, 3),
('TRANS01', 'Kit de Embrague', 230.00, 15, 2, TRUE, 4),
('TRANS02', 'Aceite de Transmisión', 18.50, 60, 10, TRUE, 4),
('EXHAUST01', 'Silenciador', 150.00, 10, 2, TRUE, 5),
('ELEC01', 'Batería de 12V', 95.00, 25, 5, TRUE, 6),
('ACCESS01', 'Tapetes de Goma', 25.00, 100, 10, TRUE, 7),
('BODY01', 'Espejo Retrovisor Derecho', 45.00, 20, 2, TRUE, 8),
('COOLING01', 'Radiador', 120.00, 10, 2, TRUE, 9),
('STEERING01', 'Caja de Dirección', 320.00, 5, 1, TRUE, 10);

-- Insertar usuarios de ejemplo
INSERT INTO users (password, dni, name, email, is_admin) VALUES
('password123', '12345678', 'Juan Pérez', 'juan.perez@example.com', FALSE),
('adminpass', '87654321', 'Admin User', 'admin@example.com', TRUE),
('userpass', '45678912', 'Ana Gómez', 'ana.gomez@example.com', FALSE);

-- Insertar tarjetas de ejemplo
INSERT INTO cards (card_number, card_expiry, card_cvv) VALUES
('1234567890123456', '12/25', '123'),
('6543210987654321', '06/24', '456'),
('1111222233334444', '09/26', '789');