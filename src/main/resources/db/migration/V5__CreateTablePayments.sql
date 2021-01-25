CREATE TABLE IF NOT EXISTS payments(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id bigint(20),
    amount int,
    paid_at datetime,
    status tinyint(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_PaymentOrder FOREIGN KEY (order_id) REFERENCES orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
