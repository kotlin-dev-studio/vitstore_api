CREATE TABLE IF NOT EXISTS orders(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20),
    address varchar(255) NOT NULL,
    contact_number varchar(10) NOT NULL,
    total_price decimal,
    status tinyint(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_UserOrder FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
