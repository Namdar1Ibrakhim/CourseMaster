# CourseMaster

CourseMaster — это учебное приложение на основе Spring Boot с использованием PostgreSQL и RabbitMQ.

## Требования
- Docker
- Docker Compose
- JDK 17+

## Сборка и запуск приложения

1. Склонируйте репозиторий:
   ```bash
   git clone <URL-репозитория>
   cd CourseMaster
   ```

2. Соберите приложение с помощью Maven:
   ```bash
   ./mvnw clean package
   ```

3. Запустите контейнеры Docker:
   ```bash
   docker-compose up --build
   ```

4. Приложение будет доступно по адресу:
   ```
   http://localhost:8080
   ```

## Стек технологий
- Spring Boot
- PostgreSQL
- RabbitMQ
- Docker
- Docker Compose
- Liquibase
- Swagger

## Управление базой данных

Для подключения к базе данных используйте команду:
```bash
docker exec -it course-master-db psql -U postgres -d course_master
```

## Swagger UI
Документация API доступна по адресу:
```
http://localhost:8080/swagger-ui/index.html
```

## Логи
Чтобы посмотреть логи контейнера приложения:
```bash
docker logs -f course-master-app
```

