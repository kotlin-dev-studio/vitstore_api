CREATE TABLE IF NOT EXISTS order_details(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id bigint(20),
    product_id bigint(20),
    quantity int,
    sub_total decimal,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_OrderDetailOrder FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT FK_OrderDetailProduct FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
