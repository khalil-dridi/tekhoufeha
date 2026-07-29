CREATE TABLE password_reset_tokens (

                                       id BINARY(16) NOT NULL,

                                       token VARCHAR(255) NOT NULL UNIQUE,

                                       expiry_date DATETIME NOT NULL,

                                       auth_user_id BINARY(16) NOT NULL,

                                       PRIMARY KEY (id),

                                       CONSTRAINT fk_password_reset_user
                                           FOREIGN KEY (auth_user_id)
                                               REFERENCES auth_users(id)
                                               ON DELETE CASCADE
);