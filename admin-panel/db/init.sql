DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    code char(3) PRIMARY KEY
);

CREATE TABLE countries (
    code char(2) PRIMARY KEY,
    name TEXT,
    currency char(3) REFERENCES currencies(code),
    currencies TEXT[]
);

INSERT INTO currencies VALUES ('KES');
INSERT INTO currencies VALUES ('PKR');

INSERT INTO countries VALUES ('KE', 'kenya', 'KES', '{"KES","PKR"}');
INSERT INTO countries VALUES ('PK', 'pakistan', 'PKR', '{"PKR","KES"}');
