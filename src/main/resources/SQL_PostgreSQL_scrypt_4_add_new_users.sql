-- Добавление новых пользователей

-- Пользователь с ролью GUEST
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES ('guest', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'guest@example.com', 'Guest User', '1990-01-01', '123 Street, City'); -- пароль: '1234'

-- Пользователь с ролями GUEST и USER
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES ('user', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'user@example.com', 'Regular User', '1991-02-02', '456 Avenue, City'); -- пароль: '1234'

-- Пользователь с ролями GUEST, USER и ADMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES ('admin', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'admin@example.com', 'Admin User', '1980-03-03', '789 Boulevard, City'); -- пароль: '1234'

-- Пользователь с ролями GUEST, USER, ADMIN и SUPERSAMIN
INSERT INTO users (user_name, user_password, user_email, user_full_name, user_date_birth, user_address)
VALUES ('superadmin', '$2a$05$ppC7GcE5CtLHvX2gUHMFpeK7wpKf7Kdzfxp55b/.bEBZxDT9H7oe.', 'superadmin@example.com', 'Super Admin User', '1970-04-04', '101 Street, City'); -- пароль: '1234'


-- Привязка ролей для новых пользователей

-- Пользователь с ролью GUEST
INSERT INTO user_roles (user_id, role_id) VALUES
    ((SELECT user_id FROM users WHERE user_name = 'guest'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST'));

-- Пользователь с ролями GUEST и USER
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'user'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'user'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER'));

-- Пользователь с ролями GUEST, USER и ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'admin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'admin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN'));

-- Пользователь с ролями GUEST, USER, ADMIN и SUPERSAMIN
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_GUEST')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_USER')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_ADMIN')),
                                              ((SELECT user_id FROM users WHERE user_name = 'superadmin'), (SELECT role_id FROM roles WHERE role_name = 'ROLE_SUPERADMIN'));
