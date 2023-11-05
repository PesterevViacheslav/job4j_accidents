CREATE SEQUENCE IF NOT EXISTS users_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS users (
    id INTEGER NOT NULL DEFAULT nextval('users_sq'),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled boolean default true,
    authority_id INTEGER NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY(id),
    CONSTRAINT fk_users$authority FOREIGN KEY (authority_id) REFERENCES authorities(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_users$username ON users(username);