CREATE TABLE IF NOT EXISTS tokens(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    romsid varchar(255),
    type tinyint(1),
    active_token varchar(36),
    token varchar(36),
    uuid varchar(45),
    expire_date datetime,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
