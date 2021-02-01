CREATE TABLE users
(
    id       VARCHAR(255) PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE (email)
);

CREATE TABLE user_passwords
(
    id       VARCHAR(255) PRIMARY KEY,
    user_id  VARCHAR(255) NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    password VARCHAR(255) NOT NULL
);

