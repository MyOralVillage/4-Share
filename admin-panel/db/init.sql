DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    code char(3) PRIMARY KEY
);

CREATE TABLE countries (
    code char(2) PRIMARY KEY,
    name TEXT,
    currency char(3) REFERENCES currencies(code),
    currencies TEXT[] DEFAULT '{"KES", "PKR", "BDT", "USD", "INR"}'
);

INSERT INTO currencies VALUES ('KES');
INSERT INTO currencies VALUES ('PKR');
INSERT INTO currencies VALUES ('BDT');
INSERT INTO currencies VALUES ('USD');
INSERT INTO currencies VALUES ('INR');

INSERT INTO countries VALUES ('KE', 'Kenya', 'KES');
INSERT INTO countries VALUES ('PK', 'Pakistan', 'PKR');
INSERT INTO countries VALUES ('BD', 'Bangladesh', 'BDT');
INSERT INTO countries VALUES ('US', 'United States of America', 'USD');
INSERT INTO countries VALUES ('IN', 'India', 'INR');
