-- Удаление данных из таблиц
DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM roles;

-- Удаление таблиц с каскадным удалением зависимостей
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

