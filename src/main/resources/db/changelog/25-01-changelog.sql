-- liquibase formatted sql

-- changeset onish:1764100942959-1
CREATE TABLE users
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    surname  VARCHAR(255),
    login    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

