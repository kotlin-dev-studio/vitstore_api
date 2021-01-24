CREATE TABLE IF NOT EXISTS tokens(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20),
    uuid varchar(45),
    type tinyint(1),
    access_token varchar(255),
    expired_at datetime,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_OauthTokenUser FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
