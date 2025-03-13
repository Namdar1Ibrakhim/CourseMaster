-- Добавление администратора
INSERT INTO user_ (username, password, role, created_date, updated_date)
VALUES ('admin', '$2a$12$fKzGMyLHEU0dAouwckFEmu4RtTy5KfyZfBdBWsF7dd4RDIJQt921a', 'ADMIN', NOW(), NOW());

-- Добавление обычного пользователя
INSERT INTO user_ (username, password, role, created_date, updated_date)
VALUES ('user', '$2a$12$vMfi0XAulKWKFp4sHURewOePYE1xshcXnckvfOsVk/kzpkqEJW5iG', 'USER', NOW(), NOW());

-- Добавление студента
INSERT INTO student (first_name, last_name, email, user_id, created_date, updated_date)
VALUES ('TestStudent', 'TestStudent', 'namdar_ibrahim@mail.ru', 2, NOW(), NOW());

-- Добавление курса
INSERT INTO course (name, description, start_date, end_date, created_date, updated_date)
VALUES ('Mathematics', 'Introductory course on Mathematics', NOW(), NOW(), NOW(), NOW());
