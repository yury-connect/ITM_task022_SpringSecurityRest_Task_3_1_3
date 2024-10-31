-- Удаление данных из таблиц
DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM roles;

-- Удаление таблиц с каскадным удалением зависимостей
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;


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
INSERT INTO roles (role_name) VALUES ('ROLE_GUEST'), ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_SUPERADMIN');



-- 5. Заполнение таблицы users

-- Пользователи с ролью ROLE_GUEST
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('guest', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest@example.com', 'Guest User', '1990-01-01', '123 Street, City'),   -- пароль: '1234'
    ('guest1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest1@example.com', 'Guest One', '1991-01-01', '124 Street, City'),   -- пароль: '1234'
    ('guest2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest2@example.com', 'Guest Two', '1992-02-02', '125 Street, City'),   -- пароль: '1234'
    ('guest3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest3@example.com', 'Guest Three', '1993-03-03', '126 Street, City');   -- пароль: '1234'

-- Пользователи с ролью ROLE_USER
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('user', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user@example.com', 'Regular User', '1994-04-04', '127 Avenue, City'),   -- пароль: '1234'
    ('user1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user1@example.com', 'User One', '1995-05-05', '128 Avenue, City'),   -- пароль: '1234'
    ('user2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user2@example.com', 'User Two', '1996-06-06', '129 Avenue, City'),   -- пароль: '1234'
    ('user3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user3@example.com', 'User Three', '1997-07-07', '130 Avenue, City');   -- пароль: '1234'

-- Пользователи с ролью ROLE_ADMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('admin', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin@example.com', 'Admin User', '1980-07-07', 'Admin Street, City'),   -- пароль: '1234'
    ('admin1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin1@example.com', 'Admin One', '1981-08-08', 'Admin Avenue, City'),   -- пароль: '1234'
    ('admin2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin2@example.com', 'Admin Two', '1982-09-09', 'Admin Boulevard, City'),   -- пароль: '1234'
    ('admin3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin3@example.com', 'Admin Three', '1983-10-10', 'Admin Road, City');   -- пароль: '1234'

-- Пользователи с ролью ROLE_SUPERADMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES
    ('superadmin', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin@example.com', 'Super Admin User', '1970-10-10', 'Super Street, City'),   -- пароль: '1234'
    ('superadmin1', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin1@example.com', 'Super Admin One', '1971-11-11', 'Super Avenue, City'),   -- пароль: '1234'
    ('superadmin2', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin2@example.com', 'Super Admin Two', '1972-12-12', 'Super Boulevard, City'),   -- пароль: '1234'
    ('superadmin3', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin3@example.com', 'Super Admin Three', '1973-01-01', 'Super Road, City');   -- пароль: '1234'


-- 6. Заполнение таблицы user_roles
-- Связывание пользователей с ролями

-- Пользователи с ролью ROLE_GUEST
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'guest'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'guest1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'guest2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'guest3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST'));

-- Пользователи с ролью ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'user'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER'));

-- Пользователи с ролью ROLE_ADMIN (имеют роли ROLE_USER и ROLE_ADMIN)
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'admin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN'));

-- Пользователи с ролью ROLE_SUPERADMIN (имеют роли ROLE_USER, ROLE_ADMIN и ROLE_SUPERADMIN)
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_SUPERADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin1'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_SUPERADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin2'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_SUPERADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin3'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_SUPERADMIN'));
