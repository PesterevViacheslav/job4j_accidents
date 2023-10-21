CREATE SEQUENCE IF NOT EXISTS accident_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS accident (
     id INTEGER NOT NULL DEFAULT nextval('accident_sq'),
     title VARCHAR(200) NOT NULL,
     dsc VARCHAR(2000),
     address VARCHAR(200) NOT NULL,
     typ_id INTEGER NOT NULL,
     CONSTRAINT pk_accident PRIMARY KEY(id),
     CONSTRAINT fk_accident$typ FOREIGN KEY (typ_id) REFERENCES typ(id)
);