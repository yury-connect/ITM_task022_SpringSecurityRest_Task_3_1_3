-- Эти SQL-запросы создадут необходимые таблицы и добавят пользователей и роли в базе данных PostgreSQL.


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



-- 4. Заполнение таблицы roles
INSERT INTO roles (role_name) VALUES ('GUEST'), ('USER'), ('ADMIN'), ('SUPERSAMIN');



-- 5. Заполнение таблицы users

-- Пользователи с ролью GUEST
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('guest1', 'password1', 'guest1@example.com', 'Guest One', '1990-01-01', '123 Street, City'),
    ('guest2', 'password2', 'guest2@example.com', 'Guest Two', '1992-02-02', '456 Street, City'),
    ('guest3', 'password3', 'guest3@example.com', 'Guest Three', '1993-03-03', '789 Street, City');

-- Пользователи с ролью USER
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('user1', 'password4', 'user1@example.com', 'User One', '1994-04-04', '1234 Avenue, City'),
    ('user2', 'password5', 'user2@example.com', 'User Two', '1995-05-05', '5678 Avenue, City'),
    ('user3', 'password6', 'user3@example.com', 'User Three', '1996-06-06', '91011 Avenue, City');

-- Пользователи с ролью ADMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('admin1', 'password7', 'admin1@example.com', 'Admin One', '1980-07-07', 'Admin Street, City'),
    ('admin2', 'password8', 'admin2@example.com', 'Admin Two', '1981-08-08', 'Admin Avenue, City'),
    ('admin3', 'password9', 'admin3@example.com', 'Admin Three', '1982-09-09', 'Admin Boulevard, City');

-- Пользователи с ролью SUPERSAMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('superadmin1', 'password10', 'superadmin1@example.com', 'Super Admin One', '1970-10-10', 'Super Street, City'),
    ('superadmin2', 'password11', 'superadmin2@example.com', 'Super Admin Two', '1971-11-11', 'Super Avenue, City'),
    ('superadmin3', 'password12', 'superadmin3@example.com', 'Super Admin Three', '1972-12-12', 'Super Boulevard, City');



-- 6. Заполнение таблицы user_roles
-- Связывание пользователей с ролями

-- Пользователи с ролью GUEST
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'guest1'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'guest2'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'guest3'), (SELECT role_id FROM roles WHERE role_name = 'GUEST'));

-- Пользователи с ролью USER
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'user1'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user2'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user3'), (SELECT role_id FROM roles WHERE role_name = 'USER'));

-- Пользователи с ролью ADMIN (имеют роли USER, ADMIN и GUEST)
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'admin1'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin1'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin1'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin2'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin2'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin2'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin3'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin3'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin3'), (SELECT role_id FROM roles WHERE role_name = 'GUEST'));

-- Пользователи с ролью SUPERSAMIN (имеют роли USER, ADMIN, GUEST и SUPERSAMIN)
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'SUPERSAMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'SUPERSAMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'SUPERSAMIN'));

-- Эти SQL-запросы создадут необходимые таблицы и добавят пользователей и роли в базе данных PostgreSQL.