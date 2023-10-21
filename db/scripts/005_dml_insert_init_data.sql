INSERT INTO typ (id, title)
VALUES (1, 'Одна машина'),
       (2, 'Две машины'),
       (3, 'Машина и человек'),
       (4, 'Машина и велосипед');

INSERT INTO rule (id, title)
VALUES (1, 'Статья 1'),
       (2, 'Статья 2'),
       (3, 'Статья 3');

INSERT INTO accident (id, title, dsc, address, typ_id)
VALUES (1, 'accident_1', 'text_1', 'address_1', 1),
       (2, 'accident_2', 'text_2', 'address_2', 2),
       (3, 'accident_3', 'text_5', 'address_5', 3);

INSERT INTO accident_rule (rule_id, accident_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 2),
       (3, 3);
