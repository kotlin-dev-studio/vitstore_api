CREATE TABLE IF NOT EXISTS products(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name varchar(255) NOT NULL,
    price decimal NOT NULL,
    description text,
    image_url text,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
