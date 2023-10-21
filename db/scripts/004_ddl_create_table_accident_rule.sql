CREATE SEQUENCE IF NOT EXISTS accident_rule_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS accident_rule (
    id INTEGER NOT NULL DEFAULT nextval('accident_rule_sq'),
    accident_id INTEGER NOT NULL,
    rule_id INTEGER NOT NULL,
    CONSTRAINT pk_accident_rule PRIMARY KEY(id),
    CONSTRAINT fk_accident_rule$accident FOREIGN KEY (accident_id) REFERENCES accident(id),
    CONSTRAINT fk_accident_rule$rule FOREIGN KEY (rule_id) REFERENCES rule(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_accident_rule$accident$rule ON accident_rule(accident_id, rule_id);