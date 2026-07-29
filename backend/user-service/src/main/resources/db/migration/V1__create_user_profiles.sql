CREATE TABLE user_profiles (

                               id VARCHAR(36) NOT NULL,

                               auth_user_id VARCHAR(36) NOT NULL,

                               first_name VARCHAR(100),

                               last_name VARCHAR(100),

                               phone VARCHAR(20),

                               city VARCHAR(100),

                               governorate VARCHAR(100),

                               avatar_url VARCHAR(255) NOT NULL,

                               bio TEXT,

                               profile_completed BOOLEAN NOT NULL DEFAULT FALSE,

                               created_at DATETIME NOT NULL,

                               updated_at DATETIME,

                               PRIMARY KEY (id)

);