CREATE TABLE currencies (
    code varchar(3) PRIMARY KEY
);

CREATE TABLE countries (
    name TEXT PRIMARY KEY,
    currencies TEXT[]
);

INSERT INTO currencies VALUES ('KES');
INSERT INTO currencies VALUES ('PKR');

INSERT INTO countries VALUES ('kenya', '{"KES","PKR"}');
INSERT INTO countries VALUES ('pakistan', '{"PKR","KES"}');
