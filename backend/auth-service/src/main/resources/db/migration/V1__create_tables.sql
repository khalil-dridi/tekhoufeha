CREATE TABLE auth_users (
                            id BINARY(16) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            role VARCHAR(30) NOT NULL,
                            status VARCHAR(30) NOT NULL,
                            created_at DATETIME NOT NULL,
                            updated_at DATETIME NOT NULL,

                            PRIMARY KEY (id)
);


CREATE TABLE refresh_tokens (
                                id BIGINT AUTO_INCREMENT NOT NULL,
                                token VARCHAR(255) NOT NULL UNIQUE,
                                expiry_date DATETIME NOT NULL,
                                auth_user_id BINARY(16) NOT NULL,

                                PRIMARY KEY (id),

                                CONSTRAINT fk_refresh_token_user
                                    FOREIGN KEY (auth_user_id)
                                        REFERENCES auth_users(id)
                                        ON DELETE CASCADE
);