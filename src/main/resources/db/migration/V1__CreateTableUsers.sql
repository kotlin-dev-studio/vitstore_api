CREATE TABLE IF NOT EXISTS users(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    status tinyint(1),
    phone_number varchar(20),
    reset_password_token varchar(255),
    reset_password_token_expired_at datetime,
    active_token varchar(255),
    active_token_expired_at datetime,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
