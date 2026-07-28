-- V2__insert_default_admin.sql

INSERT INTO auth_users (
    id,
    email,
    password,
    role,
    status,
    created_at,
    updated_at
)
VALUES (
           UUID_TO_BIN(UUID()),
           'admin@tekhoufeha.tn',
           '$2a$10$Uc6LEjzdLTbY7MVvr93G6uHb2qt.yEevoQZwf52iYIY4Qbi4zpECC', -- mot de passe BCrypt
           'ADMIN',
           'ACTIVE',
           NOW(),
           NOW()
       );