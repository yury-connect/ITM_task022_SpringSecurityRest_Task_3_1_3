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
    ('guest1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest1@example.com', 'Guest One', '1990-01-01', '123 Street, City'),   -- пароль: '1234'
    ('guest2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest2@example.com', 'Guest Two', '1992-02-02', '456 Street, City'),   -- пароль: '1234'
    ('guest3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest3@example.com', 'Guest Three', '1993-03-03', '789 Street, City');   -- пароль: '1234'

-- Пользователи с ролью USER
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('user1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user1@example.com', 'User One', '1994-04-04', '1234 Avenue, City'),   -- пароль: '1234'
    ('user2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user2@example.com', 'User Two', '1995-05-05', '5678 Avenue, City'),   -- пароль: '1234'
    ('user3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user3@example.com', 'User Three', '1996-06-06', '91011 Avenue, City');   -- пароль: '1234'

-- Пользователи с ролью ADMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('admin1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin1@example.com', 'Admin One', '1980-07-07', 'Admin Street, City'),   -- пароль: '1234'
    ('admin2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin2@example.com', 'Admin Two', '1981-08-08', 'Admin Avenue, City'),   -- пароль: '1234'
    ('admin3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin3@example.com', 'Admin Three', '1982-09-09', 'Admin Boulevard, City');   -- пароль: '1234'

-- Пользователи с ролью SUPERSAMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('superadmin1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin1@example.com', 'Super Admin One', '1970-10-10', 'Super Street, City'),   -- пароль: '1234'
    ('superadmin2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin2@example.com', 'Super Admin Two', '1971-11-11', 'Super Avenue, City'),   -- пароль: '1234'
    ('superadmin3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin3@example.com', 'Super Admin Three', '1972-12-12', 'Super Boulevard, City');   -- пароль: '1234'



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