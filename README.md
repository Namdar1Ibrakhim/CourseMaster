# CourseMaster

CourseMaster - API для управления учебными курсами с уведомлениями и авторизацией

## Требования
- Docker
- Docker Compose
- JDK 17+

## Сборка и запуск приложения

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/Namdar1Ibrakhim/CourseMaster.git
   cd CourseMaster
   ```

2. Соберите приложение с помощью Maven:
   ```bash
   ./mvnw clean package
   ```

3. Запустите контейнеры Docker:
   ```bash
   docker-compose up -d --build
   ```

4. Приложение будет доступно по адресу:
   ```
   http://localhost:8080
   ```

## Стек технологий
- Java 17+
- Spring Boot 3+
- PostgreSQL
- RabbitMQ
- Docker
- Docker Compose
- Liquibase
- Swagger

## Swagger UI
Документация API доступна по адресу:
```
http://localhost:8080/swagger-ui/index.html
```

## Postman Collections
Коллекция Постман API доступна по адресу:
```
https://restless-star-481705.postman.co/workspace/My-Workspace~fd54e6dd-2f64-4cce-8fb6-4ab377c3c47c/collection/25164846-c05867b9-b0e8-46f7-a369-14e0c0d68ba0?action=share&creator=25164846
```
Или можете импортировать коллекцию скачав по ссылке: 
```
https://drive.google.com/file/d/1hJzup-wHA6cbSUVriZscP19MF9EhfoPD/view?usp=sharing
```

## Управление базой данных

Для подключения к базе данных используйте команду:
```bash
docker exec -it course-master-db psql -U postgres -d course_master
```

## Логи
Чтобы посмотреть логи контейнера приложения:
```bash
docker logs -f course-master-app
```

