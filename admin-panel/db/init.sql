DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    code varchar(3) PRIMARY KEY
);

CREATE TABLE countries (
    name TEXT PRIMARY KEY,
    code varchar(2),
    currency varchar(3) REFERENCES currencies(code),
    currencies TEXT[]
);

INSERT INTO currencies VALUES ('KES');
INSERT INTO currencies VALUES ('PKR');

INSERT INTO countries VALUES ('kenya', 'KE', 'KES', '{"KES","PKR"}');
INSERT INTO countries VALUES ('pakistan', 'PK', 'PKR', '{"PKR","KES"}');
