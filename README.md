CourseMaster

CourseMaster — это сервис на базе Spring Boot для управления курсами с использованием PostgreSQL, RabbitMQ и Docker.

Стек технологий

Java 17

Spring Boot

PostgreSQL

RabbitMQ

Docker

Liquibase

Swagger

Локальный запуск

Клонирование репозитория

git clone <URL вашего репозитория>
cd course-master

Сборка и запуск приложения

Сборка Docker-образа

docker build -t course-master-app .

Запуск через Docker Compose

docker-compose up -d

Доступ к сервису

После запуска приложение будет доступно по адресу:

http://localhost:8080

Swagger UI

Для просмотра и тестирования API используйте Swagger UI:

http://localhost:8080/swagger-ui/index.html

Подключение к базе данных

После запуска контейнеров можно подключиться к базе данных PostgreSQL:

docker exec -it course-master-db psql -U postgres -d course_master

Управление очередями RabbitMQ

Панель управления доступна по адресу:

http://localhost:15672

Логин и пароль по умолчанию: guest / guest
