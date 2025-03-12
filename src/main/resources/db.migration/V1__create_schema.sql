CREATE TABLE user_ (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_date TIMESTAMP,
                       deleted_date TIMESTAMP,
                       is_active BOOLEAN
);

CREATE TABLE student (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         email VARCHAR(255),
                         user_id BIGINT,
                         created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_date TIMESTAMP,
                         deleted_date TIMESTAMP,
                         is_active BOOLEAN,
                         CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES user_(id)
);

CREATE TABLE course (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        start_date TIMESTAMP,
                        end_date TIMESTAMP,
                        created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_date TIMESTAMP,
                        deleted_date TIMESTAMP,
                        is_active BOOLEAN
);

CREATE TABLE course_student (
                                course_id BIGINT NOT NULL,
                                student_id BIGINT NOT NULL,
                                PRIMARY KEY (course_id, student_id),
                                CONSTRAINT fk_course_student_course FOREIGN KEY (course_id) REFERENCES course(id),
                                CONSTRAINT fk_course_student_student FOREIGN KEY (student_id) REFERENCES student(id)
);