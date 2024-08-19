CREATE TABLE task (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(55) NOT NULL,
    description TEXT,
    dueDate DATE NOT NULL,
    finishedAt DATE,
    status VARCHAR(255) NOT NULL
);