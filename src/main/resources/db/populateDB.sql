DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO meals (userid, description, calories)
VALUES (100000, 'Ужин', 500);
INSERT INTO meals (userid, description, calories)
VALUES (100000, 'Обед', 500);
INSERT INTO meals (userid, description, calories)
VALUES (100000, 'Завтрак', 1000);
INSERT INTO meals (userid, description, calories)
VALUES (100001, 'Ужин', 510);
INSERT INTO meals (userid, description, calories)
VALUES (100001, 'Завтрак', 1500);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);