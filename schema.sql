DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS users_chat;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS all_sequence;

CREATE TABLE company
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    info       JSONB,
    password   VARCHAR(128),
    company_id BIGINT REFERENCES company (id)
);

CREATE TABLE profile
(
    id       BIGSERIAL PRIMARY KEY,
    street   VARCHAR(128),
    language CHAR(2),
    user_id  BIGINT REFERENCES users (id) NOT NULL UNIQUE
);

CREATE TABLE chat
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE users_chat
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT       NOT NULL REFERENCES users (id),
    chat_id    BIGINT       NOT NULL REFERENCES chat (id),
    created_at TIMESTAMP    NOT NULL,
    created_by VARCHAR(128) NOT NULL,
    UNIQUE (user_id, chat_id)
);

CREATE TABLE company_locale
(
    company_id  INT          NOT NULL REFERENCES company (id),
    lang        CHAR(2)      NOT NULL,
    description VARCHAR(128) NOT NULL,
    PRIMARY KEY (company_id, lang)
);

-- CREATE TABLE profile
-- (
--     user_id  BIGINT PRIMARY KEY REFERENCES users (id),
--     street   VARCHAR(128),
--     language CHAR(2)
-- );

-- CREATE TABLE users
-- (
--     username   VARCHAR(128),
--     firstname  VARCHAR(128),
--     lastname   VARCHAR(128),
--     birth_date DATE,
--     role       VARCHAR(32),
--     info       JSONB,
--     PRIMARY KEY (firstname, lastname, birth_date)
-- );


-- для GeneratedValue.TABLE
CREATE TABLE all_sequence
(
    table_name VARCHAR(32) PRIMARY KEY,
    pk_value   BIGINT NOT NULL
);

INSERT INTO all_sequence (table_name, pk_value)
VALUES ('users', 1);