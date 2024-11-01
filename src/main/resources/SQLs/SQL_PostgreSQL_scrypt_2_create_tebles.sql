
-- 1. Создание таблицы roles
CREATE TABLE roles (
                       role_id SERIAL PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL UNIQUE
);



-- 2. Создание таблицы users
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       user_name VARCHAR(255),
                       user_password VARCHAR(255),
                       user_email VARCHAR(255),
                       user_full_name VARCHAR(255),
                       user_date_birth DATE,
                       user_address VARCHAR(255)
);



-- 3. Создание таблицы связи user_roles
CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

