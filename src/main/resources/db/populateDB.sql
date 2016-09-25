DELETE FROM meals;
DELETE FROM users;
DELETE FROM user_roles;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Завтрак', 500, '2015-05-30 10:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Обед', 1000, '2015-05-30 13:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Ужин', 500, '2015-05-30 20:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Завтрак', 1000, '2015-05-31 10:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Обед', 500, '2015-05-31 13:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100000, 'Ужин', 510, '2015-05-31 20:00');
INSERT INTO meals (userid, description, calories, datetime)
VALUES (100001, 'Админский обед', 1024, '2015-05-31 15:00');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
