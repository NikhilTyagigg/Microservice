-- Orders
INSERT INTO orders (order_status, price) VALUES
                                             ('CONFIRMED', 2499.99),
                                             ('PENDING', 1299.50),
                                             ('CANCELLED', 799.00),
                                             ('CONFIRMED', 4599.99),
                                             ('PENDING', 999.99);

-- Order Items
INSERT INTO order_items (product_id, quantity, order_id) VALUES
                                                             (101, 2, 1),
                                                             (102, 1, 1),
                                                             (103, 3, 2),
                                                             (104, 2, 2),
                                                             (105, 1, 3),
                                                             (106, 4, 4),
                                                             (107, 1, 4),
                                                             (108, 2, 4),
                                                             (109, 5, 5),
                                                             (110, 1, 5);