CREATE SEQUENCE IF NOT EXISTS authorities_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS authorities (
   id INTEGER NOT NULL DEFAULT nextval('authorities_sq'),
   authority VARCHAR(50) NOT NULL,
   CONSTRAINT pk_authorities PRIMARY KEY(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_authorities$authority ON authorities(authority);